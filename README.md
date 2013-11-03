# clj-wordpress

A Clojure library to interact with wordpress through its XMLRPC library

## Usage

```
(require '[clj-wordpress.core :as wordpress])

(def config {:host "http://example.com"
  :blog-id 0
  :username "myuser"
  :password "mypass" })

(let [wp (wordpress/initialize config)]
  (wp 'getPosts nil))
```



## Testing

To test the module during development you'll need a working (empty and
testing) wordpress instance to work from

Do the following:

```
cp test/clj_wordpress/test_config.clj.tmpl test/clj_wordpress/test_config.clj
```

Then edit ```test_config.clj``` accordingly. Host points at the url of your
wordpress instance, blog-id is the blog_id in case there are multiple
wordpress blogs on the same install, username and password have to fit an
admin account...

then run

```
lein test
```

## License

Copyright © 2013 Michael Bauer

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
