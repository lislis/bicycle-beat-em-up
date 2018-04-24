(ns lisp2018.core
  (:require [play-cljs.core :as p]
            [goog.events :as events]
            [lisp2018.state :as s]
            [lisp2018.titlescreen :as title]
            [lisp2018.endscreen :as end]
            [lisp2018.gamescreen :as game])
  (:require-macros [lisp2018.music :refer [build-for-cljs]]))

(defonce game (p/create-game s/width s/height))
(defonce state (atom {}))

(def title-screen
  (reify p/Screen
    (on-show [this]
      (reset! state (assoc (s/initial-state game) :game-state :title)))
    (on-hide [this])
    (on-render [this]
      (title/draw game))))

(def end-screen
  (reify p/Screen
    (on-show [this]
      (reset! state (assoc @state :game-state :end)))
    (on-hide [this])
    (on-render [this]
      (end/draw game))))

(def main-screen
  (reify p/Screen
    (on-show [this]
      (game/setup game)
      (reset! state (assoc (s/initial-state game) :game-state :game)))
    (on-hide [this])
    (on-render [this]
      (game/draw game state)
      (reset! state (game/updt game @state end-screen)))))

(defn user-action []
  (condp = (:game-state @state)
    :game (reset! state (game/punch @state))
    :title (p/set-screen game main-screen)
    :end (p/set-screen game title-screen)))

(defn handle-keydown [event]
  (let [key (.-keyCode event)]
    (condp = key
      32 (user-action)
      nil)))

(events/listen js/window "keydown" handle-keydown)

(doto game
  (p/start)
  (p/set-screen title-screen))

;; uncomment to generate a song and play it!

;;(defonce audio (js/document.createElement "audio"))
;;(set! (.-src audio) (build-for-cljs))
;;(.play audio)
