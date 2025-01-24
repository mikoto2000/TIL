packadd denops
packadd ddt.vim
packadd ddt-ui-terminal
packadd ddt-ui-shell

""" from https://github.com/Shougo/shougo-s-github/blob/master/vim/rc/ddt.vim

let g:denops#server#deno_args = [
    \   '-q',
    \   '-A',
    \ ]
let g:denops#server#deno_args += ['--unstable-ffi']

nnoremap <Leader>s  <Cmd>call ddt#start(#{
      \   name: t:->get('ddt_ui_shell_last_name',
      \                 'shell-' .. win_getid()),
      \   ui: 'shell',
      \ })<CR>
nnoremap <Leader>t  <Cmd>call ddt#start(#{
      \   name: t:->get('ddt_ui_terminal_last_name',
      \                 'terminal-' .. win_getid()),
      \   ui: 'terminal',
      \ })<CR>


call ddt#custom#patch_global(#{
      \   uiParams: #{
      \     shell: #{
      \       nvimServer: '~/.cache/nvim/server.pipe',
      \       prompt: '=\\\>',
      \       promptPattern: '\w*=\\\> \?',
      \     },
      \     terminal: #{
      \       nvimServer: '~/.cache/nvim/server.pipe',
      \       command: ['bash'],
      \       promptPattern: has('win32') ? '\f\+>' : '\w*% \?',
      \     },
      \   },
      \ })

""" {{{ for ddt-ui-shell
augroup ddt-shell
    autocmd!
    autocmd FileType ddt-shell nnoremap <buffer> <C-n> <Cmd>call ddt#ui#do_action('nextPrompt')<CR>
    autocmd FileType ddt-shell nnoremap <buffer> <C-p> <Cmd>call ddt#ui#do_action('previousPrompt')<CR>
    autocmd FileType ddt-shell nnoremap <buffer> <C-y> <Cmd>call ddt#ui#do_action('pastePrompt')<CR>
    autocmd FileType ddt-shell nnoremap <buffer> <CR> <Cmd>call ddt#ui#do_action('executeLine')<CR>
    autocmd FileType ddt-shell inoremap <buffer> <CR> <Cmd>call ddt#ui#do_action('executeLine')<CR>
augroup END
""" }}} for ddt-ui-shell

