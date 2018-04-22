(ns lisp2018.logic
  (:require [play-cljs.core :as p]
            [lisp2018.state :as s]))

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

(defn spawn-enemy [enemy-vec types]
  (let [sprite (nth types (rand (- (count types) 1)))
        e (s/build-enemy sprite)]
    (conj enemy-vec e)))

(defn new-rand-enemy-timer-max []
  (* (+ (rand 4) 1) 500))

(defn enemy-timer [state game]
  (let [dt (p/get-delta-time game)
        timer (+ (:enemy-timer state) dt)
        spawn? (> timer (:enemy-timer-max state))]
    (if spawn?
      (-> state
          (assoc :enemies (spawn-enemy (:enemies state) (:enemy-types state)))
          (assoc :enemy-timer 0)
          (assoc :enemy-timer-max (new-rand-enemy-timer-max)))
      (assoc state :enemy-timer timer))))

(defn update-enemy [e]
  (if  (:alive e)
    (let [x (:x e)
          v (:v e)
          alive? (if (< x 0) false true)
          new-x (- x v)
          new-e (if alive?
                  (assoc e :x new-x)
                  (assoc e :alive false))]
      new-e)
    e))

(defn update-enemies [state]
  (let [es (:enemies state)
        new-es (mapv update-enemy es)]
    (assoc state :enemies new-es)))

(defn cleanup-enemies [state]
  (let [es (:enemies state)
        f (filterv (fn [x] (:alive x)) es)]
    (assoc state :enemies f)))

(defn is-colliding-with [e px]
  (let [pw (+ px s/sprite-display-w)
        ex (:x e)
        in-between? (if (and
                         (< ex pw)
                         (> ex px))
                      true
                      false)]
    (assoc e :alive (not in-between?))))

(defn collision [state]
  (let [px (- (:x state) 5)
        es (:enemies state)
        new-es (mapv (fn [e] (is-colliding-with e px)) es)
        count-dead (count (filterv (fn [x] (not (:alive x))) new-es))
        collision? (> count-dead 0)
        punching? (:is-punching state)
        lives (if (and collision? (not punching?))
                (- (:lives state) 1)
                (:lives state))
        score (if (and collision? punching?)
                (+ 10 (:score state))
                (:score state))]
    (-> state
        (assoc :enemies new-es)
        (assoc :lives lives)
        (assoc :score score))))
