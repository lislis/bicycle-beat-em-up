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
                     [:fill {:color "red"}
                      [:text {:value "Bicycle Beat'em Up" :x 20 :y 50}]
                      [:text {:value "Press <space> to start" :x 20 :y 100}]]]])))

(defn updt [game])
