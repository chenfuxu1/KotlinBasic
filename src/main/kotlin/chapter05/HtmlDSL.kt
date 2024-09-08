package chapter05

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-08 16:41
 *
 * HTML DSl
 **/
fun main() {

}

fun html(block: BlockNode.() -> Unit): BlockNode {
    val html = BlockNode("html")
    html.block()
    return html
}

fun BlockNode.head(block: BlockNode.() -> Unit): BlockNode {
    val head = BlockNode("head")
    head.block()
    this.mChildren += head
    return head
}

fun BlockNode.body(block: BlockNode.() -> Unit): BlockNode {
    val body = BlockNode("body")
    body.block()
    this.mChildren += body
    return body
}

interface Node {
    fun render(): String
}

class StringNode(val content: String) : Node {
    override fun render(): String {
        return content
    }

}

class BlockNode(val name: String) : Node {
    val mChildren = ArrayList<Node>()
    val mProperties = HashMap<String, Any>()

    override fun render(): String {
        return """<$name ${
            mProperties.map { "${it.key}='${it.value}'" }.joinToString(" ")
        }>${mChildren.joinToString("") { it.render() }}</$name>"""
    }

    operator fun String.invoke(block: BlockNode.() -> Unit): BlockNode {
        val node = BlockNode(this)
        node.block()
        this@BlockNode.mChildren += node
        return node
    }

    operator fun String.invoke(value: Any) {
        this@BlockNode.mProperties[this] = value
    }

    operator fun String.unaryPlus() {
        this@BlockNode.mChildren += StringNode(this)
    }
}