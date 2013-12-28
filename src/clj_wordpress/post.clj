(ns clj-wordpress.post
  (:use clj-wordpress.core))

(defn new-post
  ""
  [config content tags]
  (with-wp config (wp 'newPost [{:post_content content :tags tags}])))
