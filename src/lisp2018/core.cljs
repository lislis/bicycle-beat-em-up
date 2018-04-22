(ns lisp2018.core
  (:require [play-cljs.core :as p]
            [goog.events :as events]
            [lisp2018.logic :as l]
            [lisp2018.state :as s]
            [lisp2018.ui :as ui])
  (:require-macros [lisp2018.music :refer [build-for-cljs]]))


(defonce game (p/create-game s/width s/height))
(defonce state (atom {}))

(def main-screen
  (reify p/Screen
    (on-show [this]
      (p/load-image game s/sprite-url)
      (reset! state (s/initial-state game)))
    (on-hide [this])
    (on-render [this]
      (let [{:keys [x y current lives score]} @state]
        (p/render game
                  [(ui/draw-status-bar s/width lives score)
                   (ui/draw-player current x y s/sprite-display-w s/sprite-display-h)
                   ]))
      ;;(js/console.log (:is-punching @state) (:punch-timer @state))
      (swap! state
             (fn [state]
               (-> state
                   (l/punch-timer game)
                   (l/update-player-state)
                   (l/update-player-sprite)
                   ))))))

(defn punch []
  (if-not (:is-punching @state)
    (do
      (js/console.log "PUNCH")
      (swap! state assoc :is-punching true))))

(defn handle-keydown [event]
  (let [key (.-keyCode event)]
    (condp = key
      ;;"68" (punch)
      32 (punch)
      nil)))

(events/listen js/window "keydown" handle-keydown)

;; (events/listen js/window "resize"
;;   (fn [event]
;;     (p/set-size game js/window.innerWidth js/window.innerHeight)))

(doto game
  (p/start)
  (p/set-screen main-screen))

;; uncomment to generate a song and play it!

;;(defonce audio (js/document.createElement "audio"))
;;(set! (.-src audio) (build-for-cljs))
;;(.play audio)
