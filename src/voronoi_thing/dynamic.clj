(ns voronoi-thing.dynamic
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

(defn key-typed [state event]
  (println "key-typed")
  state)

(defn mouse-clicked [state event]
  (println "mouse-clicked")
  (let [points (conj (:points state) [(:x event) (:y event)])]
    (merge state
           {:points points
            :voronoi (voronoi points)})))

(defn draw-state [state]
  (q/background 0)
  (if (contains? state :voronoi)
    (let [regions (.getRegions (:voronoi state))]
      (doall
      (for [i (range (count regions))]
        (do
          (q/fill (/ (* 255 i) (count regions)) 255 255)
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
