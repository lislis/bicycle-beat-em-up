(ns lisp2018.ui
  (:require [play-cljs.core :as p]))

(defn draw-status-bar [width lives score]
  [:div {:x -1 :y -1}
   [:fill {:color "lightblue"}
    [:stroke {:color "transparent"}
     [:rect {:x 0 :y 0 :width (+ width 1) :height 25}]]]
   [:fill {:color "red"}
    (for [r (range lives)]
      [:rect {:x (+ 10 (* r 17)) :y 7 :width 10 :height 10}])]
   [:fill {:color "black"}
    [:text {:value (str "Score: " score) :x 320 :y 18 :size 16 :style :bold}]]])

(defn draw-player [sprite x y sprite-w sprite-h]
  [:div {:x x :y y :width sprite-w :height sprite-h}
   sprite])
