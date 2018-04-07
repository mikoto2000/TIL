# -*- coding: utf8 -*-

from pandocfilters import toJSONFilter, Link, Str

def myfilter(key, value, form, meta):
    if key == 'Link':
        return Str("replaced_text")

if __name__ == "__main__":
    toJSONFilter(myfilter)

