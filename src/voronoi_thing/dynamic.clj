(ns voronoi_thing.dynamic
  (:import  [megamu.mesh Voronoi])
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [thi.ng.math.core :as mc]
            [thi.ng.geom.core :as gc]
            [thi.ng.geom.vector :as gv]
            [thi.ng.geom.polygon :as gp]))

(defn voronoi [points] 
  (Voronoi. (into-array (map float-array points))))

(defn update-state [state]
  (merge state
         {:frame (inc (:frame state))}))

(def colors [[255 93 48]
             [1 163 160]
             ])

(defn polar-to-cartesian [radius angle]
  [(* radius (Math/cos angle))
   (* radius (Math/sin angle))])

(defn cartesian-to-polar [[x y]]
  [(Math/sqrt (+ (* x x) (* y y)))
   (Math/atan2 y x)])

(defn get-rand-points [n]
  (loop [i 0
         points []]
    (if (= i n)
      points
      (recur (inc i)
             (conj points 
                   [(q/random (q/width)) 
                    (q/random (q/height))])))))

(defn flip-points [points]
  (let [half-width (/ (q/width) 2)]
    (mapv (fn [[x y]] [(+ half-width (- half-width x)) y])
          points)))

(defn rotate-points [points]
  (let [center [(/ (q/width) 2) (/ (q/height) 2)]]
    (mapv #(let [polar (cartesian-to-polar (mapv - % center))]
             (mapv + 
                   (polar-to-cartesian 
                     (first polar)
                     (mod (+ (second polar) Math/PI) (* Math/PI 2)))
                   center))
          points)))

(defn render-polygon [points]
  )

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
                {:points (get-rand-points 25)}))
    :r (do
         (println "r")
         (merge state
                {:points (get-rand-radial-points 25)}))
    :s (do
         (println "s")
         (merge state
                {:frame 0}))
    state))

(defn mouse-clicked [state event]
  (println "mouse-clicked")
  (let [points (conj (:points state) [(:x event) (:y event)])]
    (merge state
           {:points points
            :voronoi (voronoi points)})))

(defn get-timestamp []
  (format "%05d_%02d_%02d_%02d_%02d_%02d_%03d"
          (q/year) (q/month) (q/day) 
          (q/hour) (q/minute) (q/seconds)
          (mod (System/currentTimeMillis) 1000)))

(defn save-pics [state]
  (let [timestamp (get-timestamp)]
    (q/save (format "output/%s_render" 
                    timestamp))))

(defn draw-state [state]
  (q/color-mode :rgb)
  (q/background 0)
  (if (not (zero? (count (:points state))))
    ; mirror all the points
    (let [extra-points (concat (:points state) (rotate-points (:points state)))
    ; get voronoi shapes
          voronoi (voronoi extra-points)
          regions (.getRegions voronoi)
    ; migrate voronoi MPolygon to thi.ng Polygon2
          polygons (map #(gp/polygon2 (.getCoords %)) regions)
          ]
    ; render thi.ng Polygon2 shapes
      (doall
        (for [i (range (count regions))]
          (do
            (apply q/fill (conj (nth colors (mod i (count colors))) 200))
            (apply q/stroke (conj (nth colors (mod i (count colors))) 200))
            (q/stroke-weight 0)
            (.draw (nth regions i) (quil.applet/current-applet))
            ;(q/stroke 0)
            ;(q/stroke-weight 5)
            ;(apply q/point (nth extra-points i))
            )))
      (when (= (:frame state) 1)
        (save-pics state)))
    (q/text "click anywhere" 100 100)))
