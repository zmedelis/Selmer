(ns selmer.pretext-test
  (:require
    [selmer.parser :as parser]
    [clojure.test :as t]))

(parser/add-tag! :pretext
  (fn [_args context-map]
    (str "[" (:selmer/preceding-text context-map) "]")))

(t/deftest buffer-accumulation
  (t/is (= "A[A]B[A[A]B]C"
          (parser/render "{{A}}{% pretext %}B{% pretext %}C" {:A "A"}))))
