(ns lisp2018.core
  (:require [play-cljs.core :as p]
            [goog.events :as events]
            [lisp2018.state :as s]
            [lisp2018.titlescreen :as title]
            [lisp2018.gamescreen :as game])
  (:require-macros [lisp2018.music :refer [build-for-cljs]]))


(defonce game (p/create-game s/width s/height))
(defonce state (atom {}))

(def title-screen
  (reify p/Screen
    (on-show [this]
      ;;(title/setup game)
      )
    (on-hide [this])
    (on-render [this]
      (title/draw game)
      ;;(title/updt game)
      )))

(def main-screen
  (reify p/Screen
    (on-show [this]
      (game/setup game)
      (reset! state (s/initial-state game)))
    (on-hide [this])
    (on-render [this]
      (game/draw game state)
      (reset! state (game/updt game @state)))))

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

(doto game
  (p/start)
  (p/set-screen title-screen))

;; uncomment to generate a song and play it!

;;(defonce audio (js/document.createElement "audio"))
;;(set! (.-src audio) (build-for-cljs))
;;(.play audio)
