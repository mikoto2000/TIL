let s:suite = themis#suite('MyCalcSuite')
let s:assert = themis#helper('assert')

function! s:suite.AddTest()
  let actual = calc#Add(1, 1)
  let expected = 2
  call s:assert.equal(expected, actual)
endfunction


