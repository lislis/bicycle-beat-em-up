(ns lisp2018.gamescreen
  (:require [play-cljs.core :as p]
            [lisp2018.logic :as l]
            [lisp2018.state :as s]
            [lisp2018.ui :as ui]))


(defn setup [game]
  (s/load-assets game))

(defn draw [game state]
  (let [{:keys [x y
                current lives score
                bg1 bg2
                enemies]} @state]
    (p/render game
              [(ui/draw-bg bg1)
               (ui/draw-bg bg2)
               (ui/draw-status-bar s/width lives score)
               (ui/draw-player current x y s/sprite-display-w s/sprite-display-h)
               (ui/draw-enemies enemies)])))

(defn update [game state]
  (-> state
      (l/move-bg :bg1 :bg2)
      (l/enemy-timer game)
      (l/hurt-timer game)
      (l/punch-timer game)
      (l/update-enemies)
      (l/collision)
      (l/update-player-state)
      (l/update-player-sprite)
      (l/cleanup-enemies)))
