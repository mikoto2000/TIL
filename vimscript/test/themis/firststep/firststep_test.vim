let s:suite = themis#suite('FirststepSuite')
let s:assert = themis#helper('assert')

function! s:suite.FirststepTest()
  let actual = 1 + 1
  let expected = 2
  call s:assert.equal(expected, actual)
endfunction

