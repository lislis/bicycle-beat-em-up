(ns lisp2018.ui
  (:require [play-cljs.core :as p]))

(defn draw-bg [bg]
  (let [x (:x bg)
        y (:y bg)
        w (:width bg)
        offset-x (- (- w x))
        sprite (:sprite bg)]
    [:div {:width w}
     [:div {:x x :y y} sprite]
     [:div {:x offset-x :y y} sprite]]))

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

(defn draw-enemy [e]
  [:div {:x (:x e) :y (:y e) :width (:w e) :height (:h e)}
   (:sprite e)])

(defn draw-enemies [enemies]
  (mapv draw-enemy enemies))
