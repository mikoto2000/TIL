" path の絶対パスを求めて返却する。
function! first#abs(path)
    return fnamemodify(a:path, ":p")
endfunc

" path がルートディレクトリかどうかを判定する
" TODO: windows 対応
function! first#is_root_directory(path)
    let l:target = fnamemodify(a:path, ":p")
    " 最後に '/' があるかないかで '/' or '/../' になるので、
    " どちらかだったらルートディレクトリと判断する。
    if target == "/" || target == "/../"
        return 1
    else
        return 0
    endif
endfunc

" dir とその親ディレクトリを巡っていって、 fileName を探す。
" ルートディレクトリまで巡っても見つからなかったら諦める。
" 見つかったら、そのファイルのパスを返却する。
" TODO: windows 対応
function! first#find(dir, fileName)
    return first#findDir(a:dir, a:fileName) . "/" . a:fileName
endfunc

" dir とその親ディレクトリを巡っていって、 fileName を探す。
" ルートディレクトリまで巡っても見つからなかったら諦める。
" 見つかったら、そのファイルが存在するディレクトリのパスを返却する。
" TODO: windows 対応
function! first#findDir(dir, fileName)
    " ディレクトリ末尾にセパレータがあった場合、削除
    let l:absDir = substitute(first#abs(a:dir), "/$", "", "g")

    " ファイルの絶対パスを取得
    let l:filePath = first#abs(l:absDir . "/" . a:fileName)

    if filereadable(filePath) == 1
        " ファイルが見つかったらファイルパスを返却する
        return l:absDir
    elseif first#is_root_directory(a:dir) == 1
        " ファイルが見つからない、かつ、
        " ここがルートディレクトリならば 0 を返却する
        return 0
    else
        " ファイルが見つからない、かつ、
        " ここがルートディレクトリ出ないならば、
        " 再帰的に親ディレクトリを探す。
        let l:parentDir = fnamemodify(l:absDir, ":h")
        return first#findDir(l:parentDir, a:fileName)
    endif
endfunc


" vim-javaclasspath のためのクラスパスファイルを探して、
" バッファーローカル変数に設定する
" TODO: l:classpath が取得できなかった時の処理
function! first#set_classpath(dir)
    let l:classpath = first#find(a:dir, ".classpath")
    let l:config = {'filename':classpath}

    " 定義済みのバッファローカル変数を削除
    if (exists('b:javaclasspath_config'))
        unlet b:javaclasspath_config
    endif

    " 新規バッファローカル変数を設定
    let b:javaclasspath_config = javaclasspath#get_config()
    let b:javaclasspath_config['eclipse'] = l:config
endfunc

" カレントディレクトリが eclipse プロジェクト内ならば、
" そのクラスパスを取得する。
" テストが入るのが邪魔だけど目をつぶろう。
" TODO: windows 対応
function! first#get_project_classpath()
    call first#set_classpath('.')
    let l:classpaths = javaclasspath#source_path() . ':' . javaclasspath#classpath()
    let l:classpath_array = split(l:classpaths, ':')
    let l:return_classpath_array = []
    for classpath in l:classpath_array
        " 取得したクラスパスが絶対パスならそのまま、
        " 相対パスならばプロジェクトルートからの相対パスと認識
        if classpath[0] == '/'
            call add(l:return_classpath_array, classpath)
        else
            call add(l:return_classpath_array, first#findDir('.', '.classpath') . '/' . classpath)
        endif
    endfor
    " クラスパス文字列として返却する。
    return join(l:return_classpath_array, ':')
endfunc
