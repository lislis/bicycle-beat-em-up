(ns lisp2018.titlescreen
  (:require [play-cljs.core :as p]
            [lisp2018.logic :as l]
            [lisp2018.state :as s]
            [lisp2018.ui :as ui]))

(defn setup [game])

(defn draw [game]
  (let []
    (p/render game [
                    [:div {:width 300 :height 50}
                     [:fill {:color "black"}
                      [:text {:value "Bicycle"
                              :x 20 :y 100
                              :style :bold
                              :size 42
                              :font "Georgia"}]
                      [:text {:value "Beat'em Up"
                              :x 20 :y 150
                              :style :bold
                              :size 42
                              :font "Georgia"}]
                      [:text {:value "Press <space> to start" :x 20 :y 190
                              :size 20}]
                      [:text {:value "And <space> to beat up" :x 20 :y 220
                              :size 20}]]]])))

(defn updt [game])
