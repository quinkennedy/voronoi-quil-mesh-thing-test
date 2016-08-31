set tabstop=2
set softtabstop=2
set shiftwidth=2
set expandtab
set colorcolumn=80
highlight colorcolumn ctermbg=darkgray
set nocompatible
filetype off
set rtp+=~/.vim/bundle/Vundle.vim
call vundle#begin()
Plugin 'VundleVim/Vundle.vim'
Plugin 'tpope/vim-fireplace'
Plugin 'kien/rainbow_parentheses.vim'
call vundle#end()
filetype plugin indent on
nnoremap <F5> :Require<cr>
nnoremap <F6> :Require!<cr>

set exrc
set secure
