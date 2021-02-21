package gui

enum class BufferMode {
    Single,
    Double{
        override fun toBufferSize(): Int = 2
          },
    Triple{
        override fun toBufferSize(): Int = 3
          },
    ;

    open fun toBufferSize(): Int = 1
}