(ns lisp2018.endscreen
  (:require [play-cljs.core :as p]
            [lisp2018.logic :as l]
            [lisp2018.state :as s]
            [lisp2018.ui :as ui]))

(defn setup [game])

(defn draw [game state]
  (let []
    (p/render game [
                    [:div {:width 300 :height 50}
                     [:fill {:color "black"}
                      [:text {:value "Game Over"
                              :x 20 :y 100
                              :style :bold
                              :size 42
                              :font "Georgia"}]

                      [:text {:value (str "You made " (:score @state) " before losing your only life")
                              :x 20 :y 150
                              :size 20}]
                      [:text {:value "Press <space> to start over" :x 20 :y 190 :size 20}]]]])))

(defn updt [game])
