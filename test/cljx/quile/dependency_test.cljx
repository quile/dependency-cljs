(ns quile.dependency-test
  #+clj (:require [clojure.test :as t :refer (is deftest)]
                  [quile.dependency :refer :all])
  #+cljs (:require-macros [cemerick.cljs.test :refer (is deftest)])
  #+cljs (:require cemerick.cljs.test
                   [quile.dependency :refer
                             (graph depend transitive-dependents transitive-dependencies topo-sort topo-comparator)])
  )

;; building a graph like:
;;
;;       :a
;;      / |
;;    :b  |
;;      \ |
;;       :c
;;        |
;;       :d
;;
(def g1 (-> (graph)
            (depend :b :a)   ; "B depends on A"
            (depend :c :b)   ; "C depends on B"
            (depend :c :a)   ; "C depends on A"
            (depend :d :c))) ; "D depends on C"

;;      'one    'five
;;        |       |
;;      'two      |
;;       / \      |
;;      /   \     |
;;     /     \   /
;; 'three   'four
;;    |      /
;;  'six    /
;;    |    /
;;    |   /
;;    |  /
;;  'seven
;;
(def g2 (-> (graph)
            (depend 'two   'one)
            (depend 'three 'two)
            (depend 'four  'two)
            (depend 'four  'five)
            (depend 'six   'three)
            (depend 'seven 'six)
            (depend 'seven 'four)))

(deftest t-transitive-dependencies
  (is (= #{:a :c :b}
         (transitive-dependencies g1 :d)))
  (is (= '#{two four six one five three}
         (transitive-dependencies g2 'seven))))

(deftest t-transitive-dependents
  (is (= '#{four seven}
         (transitive-dependents g2 'five)))
  (is (= '#{four seven six three}
         (transitive-dependents g2 'two))))

(deftest t-topo-comparator
  (is (= '(:a :b :d :foo)
         (sort (topo-comparator g1) [:d :a :b :foo])))
  (is (= '(three five seven nine eight)
         (sort (topo-comparator g2) '[three seven nine eight five]))))

(deftest t-topo-sort
  (is (= '(one two three five six four seven)
         (topo-sort g2))))

(deftest t-no-cycles
  (is (thrown? #+clj Exception #+cljs js/Error
               (-> (graph)
                   (depend :a :b)
                   (depend :b :c)
                   (depend :c :a)))))

(deftest t-no-self-cycles
  (is (thrown? #+clj Exception #+cljs js/Error
               (-> (graph)
                   (depend :a :b)
                   (depend :a :a)))))

#+cljs (set! *main-cli-fn* #()) ;; node.js fu
