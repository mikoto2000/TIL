README
======

setup
-----

```sh
pip install pandocfilters -t vendor
```

test
----

```sh
pandoc -f markdown -t json test.md -o test.json
cat test.json | PYTHONPATH=./vendor python myfilter.py
cat test.json | PYTHONPATH=./vendor python myfilter.py | pandoc -f json -t html5 -
```

run with pandoc
---------------

```sh
PYTHONPATH=vendor pandoc --filter=./myfilter.py -t html5 -i test.md
```

