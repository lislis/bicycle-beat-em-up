(ns lisp2018.state
  (:require [play-cljs.core :as p]))

(def width 420)
(def height 320)

(def sprite-url "bikesprite.png")
(def sprite-item-w 160)
(def sprite-item-h 100)

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
                      :sx (* 3 sprite-item-w)}]]
    {:x 40
     :y 220
     :state :idle
     :lives 4
     :current idle
     :sprites {:idle idle
               :punch punch
               :hurt hurt
               :dead dead}
     :score 0
     :is-punching false
     :punch-timer 0
     :punch-timer-max 200}))
