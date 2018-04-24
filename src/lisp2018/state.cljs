(ns lisp2018.state
  (:require [play-cljs.core :as p]))

(def width 420)
(def height 320)

(def sprite-url "bikesprite.png")
(def sprite-item-w 160)
(def sprite-item-h 100)

(def base-y 220)

(def enemy-url "enemysprite.png")
(def bg1-url "bg1.png")
(def bg2-url "bg2.png")

(def sprite-display-w 80)
(def sprite-display-h 50)

(defn initial-state [game]
  (let [idle [:image {:name sprite-url
                      :swidth sprite-item-w
                      :sheight sprite-item-h
                      :sx 0}]
        punch [:image {:name sprite-url
                       :swidth sprite-item-w
                       :sheight sprite-item-h
                       :sx sprite-item-w}]
        hurt [:image {:name sprite-url
                       :swidth sprite-item-w
                       :sheight sprite-item-h
                      :sx (* 2 sprite-item-w)}]
        dead [:image {:name sprite-url
                       :swidth sprite-item-w
                       :sheight sprite-item-h
                      :sx (* 3 sprite-item-w)}]
        bg1 [:image {:name bg1-url
                     :width width
                     :height 200}]
        bg2 [:image {:name bg2-url
                     :width width
                     :height 200}]
        enemy1 [:image {:name enemy-url :swidth sprite-item-w :sx 0}]
        enemy2 [:image {:name enemy-url :swidth sprite-item-w :sx (* sprite-item-w 2)}]
        enemy3 [:image {:name enemy-url :swidth sprite-item-w :sx (* sprite-item-w 3)}]
        enemy4 [:image {:name enemy-url :swidth sprite-item-w :sx (* sprite-item-w 4)}]]

    {:game-state :game
     :x 40
     :y base-y
     :state :idle
     :lives 7
     :current idle
     :sprites {:idle idle
               :punch punch
               :hurt hurt
               :dead dead}
     :score 0
     :is-punching false
     :is-hurting false
     :punch-timer 0
     :punch-timer-max 200
     :hurt-timer 0
     :hurt-timer-max 200
     :bg1 {:x 80
           :y 20
           :width width
           :sprite bg1}
     :bg2 {:x 250
           :y 70
           :width width
           :sprite bg2}
     :enemies []
     :enemy-types [enemy1 enemy2 enemy3 enemy4]
     :enemy-timer 0
     :enemy-timer-max 500}))

(defn build-enemy [sprite]
  {:x (+ width sprite-display-w)
   :y base-y
   :w sprite-display-w
   :h sprite-display-h
   :v (+ 1 (rand 2))
   :sprite sprite
   :alive true})
