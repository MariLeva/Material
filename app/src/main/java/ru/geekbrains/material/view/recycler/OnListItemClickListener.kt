package ru.geekbrains.material.view.recycler

interface OnListItemClickListener {
    fun onItemClick(dataRecycler: DataRecycler)
    fun onAddBtnClick(position: Int)
    fun onRemoveBtnClick(position: Int)
}