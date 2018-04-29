(ns lisp2018.state
  (:require [play-cljs.core :as p]))

(def width 420)
(def height 320)

(def sprite-url "bikesprite-new.png")
(def sprite-item-w 160)
(def sprite-item-h 100)

(def base-y 220)

(def enemy-url "carsprite.png")
(def bg1-url "kotti1.png")
(def bg2-url "kotti2.png")

(def sprite-display-w 100)
(def sprite-display-h 62.5)

(defn initial-state [game]
  (let [idle [:animation {:duration 200}
              [:image {:name sprite-url
                       :swidth sprite-item-w
                       :sheight sprite-item-h
                       :width sprite-display-w
                       :sx 0}]
              [:image {:name sprite-url
                       :swidth sprite-item-w
                       :sheight sprite-item-h
                       :width sprite-display-w
                       :sx sprite-item-w}]]
        punch [:image {:name sprite-url
                       :swidth sprite-item-w
                       :sheight sprite-item-h
                       :width sprite-display-w
                       :sx (* 3 sprite-item-w)}]
        hurt [:image {:name sprite-url
                      :swidth sprite-item-w
                      :sheight sprite-item-h
                      :width sprite-display-w
                      :sx (* 2 sprite-item-w)}]
        dead [:image {:name sprite-url
                      :swidth sprite-item-w
                      :sheight sprite-item-h
                      :width sprite-display-w
                      :sx (* 4 sprite-item-w)}]
        bg1 [:image {:name bg1-url
                     :width width
                     :height 200}]
        bg2 [:image {:name bg2-url
                     :width width
                     :height 200}]
        enemy1 [:image {:name enemy-url :width sprite-display-w
                        :swidth sprite-item-w :sx 0}]
        enemy2 [:image {:name enemy-url :width sprite-display-w
                        :swidth sprite-item-w :sx (* sprite-item-w 2)}]
        enemy3 [:image {:name enemy-url :width sprite-display-w
                        :swidth sprite-item-w :sx (* sprite-item-w 3)}]
        enemy4 [:image {:name enemy-url :width sprite-display-w
                        :swidth sprite-item-w :sx (* sprite-item-w 4)}]]

    {:game-state :title
     :x 40
     :y base-y
     :state :idle
     :lives 1
     :current idle
     :sprites {:idle idle
               :punch punch
               :hurt hurt
               :dead dead}
     :score 0
     :is-punching false
     :is-hurting false
     :is-dead false
     :punch-timer 0
     :punch-timer-max 200
     :hurt-timer 0
     :hurt-timer-max 200
     :dead-timer 0
     :dead-timer-max 2000
     :bg1 {:x 80
           :y 20
           :width width
           :height 200
           :sprite bg1}
     :bg2 {:x 250
           :y 70
           :width width
           :height 200
           :sprite bg2}
     :enemies []
     :enemy-types [enemy1 enemy2 enemy3 enemy4]
     :enemy-timer 0
     :enemy-timer-max 500}))

(defn build-enemy [sprite]
  {:x (+ width sprite-display-w)
   :y (+ 5 base-y)
   :w sprite-display-w
   :h sprite-display-h
   :v (+ 1.5 (rand 2))
   :sprite sprite
   :alive true})
