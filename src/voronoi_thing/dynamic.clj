(ns voronoi_thing.dynamic
  (:import  [megamu.mesh Voronoi])
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn voronoi [points] 
  (Voronoi. (into-array (map float-array points))))

(defn update-state [state]
  state)
;  ; Update sketch state by changing circle color and position.
;  (merge {:color (mod (+ (:color state) 0.7) 255)
;          :angle (+ (:angle state) 0.1)}
;         {:voronoi (voronoi (mapv vector (range 10) (range 1 11)))}))

(def colors [[255 93 48]
             [1 163 160]
             ])

(defn polar-to-cartesian [radius angle]
  [(* radius (Math/cos angle))
   (* radius (Math/sin angle))])

(defn get-rand-points [n]
  (loop [i 0
         points []]
    (if (= i n)
      points
      (recur (inc i)
             (conj points 
                   [(q/random (q/width)) 
                    (q/random (q/height))])))))

(defn get-rand-radial-points [n]
  (loop [i 0
         points []]
    (if (= i n)
      points
      (let [point (map + 
                       (polar-to-cartesian 
                         (q/random (/ (q/height) 2))
                         (q/random (* Math/PI 2)))
                       [(/ (q/width) 2) 
                        (/ (q/height) 2)])]
        (if (and (and (> (first point) 0) 
                      (> (q/width) (first point)))
                 (and (> (second point) 0)
                      (> (q/height) (second point))))
          (recur (inc i)
                 (conj points point))
          (recur i points))))))

(defn key-typed [state event]
  (println "key-typed")
  (case (:key event)
    :p (do
         (println "p")
         (merge state
                {:points (get-rand-points 50)}))
    :r (do
         (println "r")
         (merge state
                {:points (get-rand-radial-points 50)}))
    state))

(defn mouse-clicked [state event]
  (println "mouse-clicked")
  (let [points (conj (:points state) [(:x event) (:y event)])]
    (merge state
           {:points points
            :voronoi (voronoi points)})))

(defn draw-state [state]
  (q/color-mode :rgb)
  (q/background 0)
  (if (not (zero? (count (:points state))))
    (let [voronoi (voronoi (:points state))
          regions (.getRegions voronoi)]
      (doall
        (for [i (range (count regions))]
          (do
            (apply q/fill (conj (nth colors (mod i (count colors))) 255))
            (apply q/stroke (conj (nth colors (mod i (count colors))) 255))
            (.draw (nth regions i) (quil.applet/current-applet))))))
    (q/text "click anywhere" 100 100)))
  ;; Clear the sketch by filling it with light-grey color.
  ;(q/background 240)
  ;; Set circle color.
  ;(q/fill (:color state) 255 255)
  ;; Calculate x and y coordinates of the circle.
  ;(let [angle (:angle state)
  ;      x (* 150 (q/cos angle))
  ;      y (* 150 (q/sin angle))]
  ;  ; Move origin point to the center of the sketch.
  ;  (q/with-translation [(/ (q/width) 2)
  ;                       (/ (q/height) 2)]
  ;    ; Draw the circle.
  ;    (q/ellipse x y 100 100))))
