(ns lisp2018.logic
  (:require [play-cljs.core :as p]))

(defn state-machine
  "gets a state to transition to, returns next step until transition-to is achieved"
  [transition-to current]
  (let [state-map {:punch :idle
                   :idle :punch
                   :hurt :idle
                   :dead :hurt}
        next-from-map (get state-map transition-to)
        ;; if current-state == next-from-map then return next from map
        ;; else set current_state to next-from-map and call state-machine again with same argument (transition-to)
        eq (= current next-from-map)
        ]
    (if eq
      transition-to
      next-from-map)))

(defn move-bg [state bg1 bg2]
  (if-not (:is-punching state)
    (let [w (:width (bg1 state))
          b1x (:x (bg1 state))
          b2x (:x (bg2 state))
          new-b1x (if (> b1x 0)
                    (- b1x 0.5)
                    w)
          new-b2x (if (> b2x 0)
                    (- b2x 1.7)
                    w)]
      (-> state
          (assoc-in [:bg1 :x] new-b1x)
          (assoc-in [:bg2 :x] new-b2x)))
    state))

(defn punch-timer [state game]
  (if (:is-punching state)
    (let [dt (p/get-delta-time game)
          timer (+ (:punch-timer state) dt)
          stop? (> timer (:punch-timer-max state))]
      (if stop?
        (-> state
            (assoc :is-punching false)
            (assoc :punch-timer 0))
        (assoc state :punch-timer timer)))
    state))

(defn update-player-state [state]
  (let [s (:state state)
        is-punching? (:is-punching state)
        new-s (cond
                (and is-punching? (not= s :punch)) :punch
                (not is-punching?) :idle
                :else s)
        ]
    (assoc state :state new-s)))

(defn update-player-sprite [state]
  (let [sprites (:sprites state)
        current-state (:state state)
        new-sprite (current-state sprites)]
    (assoc state :current new-sprite)))
