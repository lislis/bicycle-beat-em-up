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
      (s/load-assets game)
      (reset! state (s/initial-state game)))
    (on-hide [this])
    (on-render [this]
      (let [{:keys [x y
                    current lives score
                    bg1 bg2
                    enemies]} @state]
        (p/render game
                  [(ui/draw-bg bg1)
                   (ui/draw-bg bg2)
                   (ui/draw-status-bar s/width lives score)
                   (ui/draw-player current x y s/sprite-display-w s/sprite-display-h)
                   (ui/draw-enemies enemies)
                   ]))
      ;;(js/console.log (:is-punching @state) (:punch-timer @state))
      ;; (js/console.log "sPAWN" (count (:enemies @state)))
      ;;(js/console.log (:state @state))
      (swap! state
             (fn [state]
               (-> state
                   (l/move-bg :bg1 :bg2)
                   (l/enemy-timer game)
                   (l/hurt-timer game)
                   (l/punch-timer game)
                   (l/update-enemies)
                   (l/collision)
                   (l/update-player-state)
                   (l/update-player-sprite)
                   (l/cleanup-enemies)
                   ))))))

(defn punch []
  (if-not (and (:is-punching @state) (:is-hurting @state))
    (do
      (js/console.log "PUNCH")
      (swap! state assoc :is-punching true))))

(defn handle-keydown [event]
  (let [key (.-keyCode event)]
    (condp = key
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
