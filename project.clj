(defproject quile/dependency-cljs "0.1.4"
  :description "A data structure for representing dependency graphs"
  :url "https://github.com/quile/dependency-cljs"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :cljx {:builds [{:source-paths ["src/cljx"]
                   :output-path "target/classes"
                   :rules :clj}

                  {:source-paths ["src/cljx"]
                   :output-path "target/classes"
                   :rules :cljs}

                  {:source-paths ["test/cljx"]
                   :output-path "target/test-classes"
                   :rules :clj}

                  {:source-paths ["test/cljx"]
                   :output-path "target/test-classes"
                   :rules :cljs}]}

  :cljsbuild {:test-commands {"node"    ["node" "test-node/bin/runner-none.js" "target/test-node" "target/test-node.js"]}
              :builds [{:id "dependency"
                        :source-paths ["target/classes"]
                        :compiler {:output-to "target/dependency.js"
                                   :optimizations :simple
                                   :target :nodejs
                                   :pretty-print true}}
                       {:id "test-node"
                        :source-paths ["target/classes" "target/test-classes"]
                        :compiler {:output-to     "target/test-node.js"
                                   :target :nodejs ;;; this target required for node, plus a *main* defined in the tests.
                                   :output-dir    "target/test-node"
                                   :optimizations :none
                                   :pretty-print  true}}
                       ]}

  :profiles {:dev {:hooks        [cljx.hooks]
                   :test-paths   ["target/test-classes"]
                   :dependencies [[org.clojure/clojure "1.6.0"]
                                  [org.clojure/clojurescript "0.0-2322"]]
                   :plugins      [[com.keminglabs/cljx "0.4.0" :exclusions [org.clojure/clojure]]
                                  [com.cemerick/clojurescript.test "0.3.1"]
                                  [org.bodil/lein-noderepl "0.1.11"]
                                  [lein-cljsbuild "1.0.4-SNAPSHOT"]]}
             :clj-1.4.0 {:dependencies [[org.clojure/clojure "1.4.0"]]}
             :clj-1.3.0 {:dependencies [[org.clojure/clojure "1.3.0"]]}})
