(ns selmer.pretext-test
  (:require
    [selmer.parser :as parser]
    [clojure.test :as t]))

(parser/add-tag! :pretext
  (fn [_args context-map]
    (str "[" (:selmer/preceding-text context-map) "]")))

(t/deftest buffer-accumulation
  (t/is (= "A[A]B[A[A]B]C"
          (parser/render "{{a}}{% pretext %}B{% pretext %}C" {:a "A"}))))

(t/deftest context-values
  (t/is (= {:pretext "[A]"}
          (second (parser/render-with-values
                    "{{a}}{% pretext %}" {:a "A"}))))
  (t/is (= {:TheName "[A]"
            :pretext "[A[A] ]"}
          (second (parser/render-with-values
                    "{{a}}{% pretext var-name=TheName%} {% pretext %}" {:a "A"})))))

(t/deftest refer-generated-context-vars
  (t/is (= "A[A][A]"
          (parser/render "{{a}}{% pretext var-name=X %}{{X}}" {:a "A"}))))
