# voronoi-thing

A Quil sketch using the Mesh processing lib and thi.ng clojure lib
to test some things with Voronoi

## Usage

LightTable - open `core.clj` and press `Ctrl+Shift+Enter` to evaluate the file.

Emacs - run cider, open `core.clj` and press `C-c C-k` to evaluate the file.

REPL - run `(require 'voronoi-thing.core)`.

## License

ISC License

Copyright © 2016, Quin Kennedy

Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted, provided that the above copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.

## Original Setup
for future refernce on including non-Maven-hosted jars

0. download [Mesh](http://leebyron.com/mesh/)
0. unzip Mesh to _voronoi-thing/lib/mesh/_
0. create directory for local maven repository
    - `mkdir maven_repository`
0. install `mvn`
    - `sudo apt-get install maven`
0. deploy Mesh to local repo
    - `mvn deploy:deploy-file -Dfile=lib/mesh/library/mesh.jar -DartifactId=mesh -Dversion=1.0.0 -DgroupId=megamu.mesh -Dpackaging=jar -Durl=file:maven_repository -DcreateChecksum=true`
0. deploy QuickHull3D to local repo (a dependency of Mesh)
    - `mvn deploy:deploy-file -Dfile=lib/mesh/library/quickhull3d.1.4.jar -DartifactId=quickhull3d -Dversion=1.4.0 -DgroupId=quickhull3d -Dpackaging=jar -Durl=file:maven_repository -DcreateChecksum=true`
0. include the local repository in your _project.clj_
    - `:repositories {"local" ~(str (.toURI (java.io.File. "maven_repository")))}`
0. include mesh and quickhull3d in your _project.clj_
    - `:dependencies [[megamu.mesh/mesh "1.0.0"]
                      [quickhull3d/quickhull3d "1.4.0"]]`
0. run `lein deps` to pull in all dependencies

