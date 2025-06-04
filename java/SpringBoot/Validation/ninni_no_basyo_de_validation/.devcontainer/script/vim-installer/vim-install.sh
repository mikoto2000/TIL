#!/usr/bin/env sh

echo "start install vim to /vim/AppRun..."
cd /
curl -LO "https://github.com/lxhillwind/vim-bin/releases/download/v9.0.1503/vim-v9.0.1503-linux-x64.tar.xz"
tar axf vim-v9.0.1503-linux-x64.tar.xz

echo "finished install vim."
