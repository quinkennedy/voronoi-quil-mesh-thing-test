(defproject voronoi-thing "0.1.0-SNAPSHOT"
  :description "voronoi test with Mesh & thi.ng in Quil"
  :url "http://github.com/quinkennedy/voronoi-quil-mesh-thing-test"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories {"local" ~(str (.toURI (java.io.File. "maven_repository")))}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [quil "2.4.0"]
                 [thi.ng/geom "0.0.1062"]
                 [megamu.mesh/mesh "1.0.0"]
                 [quickhull3d/quickhull3d "1.4.0"]])
