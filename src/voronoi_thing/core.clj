(ns voronoi_thing.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [voronoi_thing.dynamic :as dynamic]))

(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 30)
  ; setup function returns initial state. It contains
  ; circle color and position.
  {:color 0
   :angle 0
   :points []})

(def dpi 138)
(def inch_size [3 4])

(q/defsketch voronoi-thing
  :title "You spin my circle right round"
  :size (map #(* dpi %) inch_size)
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update-state is called on each iteration before draw-state.
  :update dynamic/update-state
  :draw dynamic/draw-state
  :key-typed dynamic/key-typed
  :mouse-clicked dynamic/mouse-clicked
  :features [:keep-on-top]
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])
