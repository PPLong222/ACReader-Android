package indi.pplong.acreader.feature.notes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import indi.pplong.acreader.core.read.BasicReadView
import indi.pplong.acreader.core.read.layout.TextLayoutHelper
import indi.pplong.acreader.core.read.model.book.BookChapter
import indi.pplong.acreader.core.read.model.ui.ContentPage

/**
 * Description:
 * @author PPLong
 * @date 3/12/25 8:05 PM
 */

@Composable
fun NotePage(modifier: Modifier = Modifier) {
    val helper = TextLayoutHelper()
    val list = arrayListOf<String>()
//    list.add("Technology has been an integral part of human civilization, shaping the way we live, work, and interact. Over the past century, technological advancements have accelerated at an unprecedented pace, transforming every aspect of our daily lives. From the invention of the telephone to the rise of artificial intelligence, technology continues to evolve, bringing both opportunities and challenges to society.")
//    list.add("The digital revolution began in the mid-20th century with the development of computers and the internet. These innovations paved the way for global communication, instant information access, and automation. The internet has connected people across continents, enabling businesses to operate internationally, students to access online education, and individuals to stay informed with real-time news.")
//    list.add("Smartphones have further revolutionized the digital landscape. They have become essential tools for communication, entertainment, and commerce. Applications like social media platforms have transformed the way people interact, share information, and even form communities. While these advancements have fostered connectivity, they have also raised concerns about privacy, data security, and digital addiction.")
//    list.add("Artificial intelligence (AI) has emerged as one of the most groundbreaking developments in recent years. AI-powered systems are now capable of performing tasks that once required human intelligence, such as image recognition, language translation, and even medical diagnosis. Machine learning algorithms continuously improve, making AI more efficient and accurate.")
//    list.add("Automation, driven by AI and robotics, has reshaped industries like manufacturing, healthcare, and finance. Businesses rely on automated systems to streamline processes, reduce costs, and improve efficiency. However, automation has also led to job displacement, forcing workers to adapt and acquire new skills. The ethical implications of AI, including bias in decision-making and its potential impact on employment, remain subjects of debate.")
//    list.add("Education has greatly benefited from technological advancements. The shift from traditional classroom learning to online education has provided students with unprecedented access to knowledge. Digital platforms, such as online courses, virtual classrooms, and educational software, have made learning more interactive and engaging.")
    list.add("科技一直是人类文明的重要组成部分，影响着我们的生活、工作和社交方式。在过去的一个世纪里，科技进步的速度达到了前所未有的高度，彻底改变了我们的日常生活。从电话的发明到人工智能的兴起，科技不断发展，为社会带来了机遇，也带来了挑战。")
    list.add("数字革命始于 20 世纪中叶，计算机和互联网的发展成为其标志。这些技术创新为全球通信、即时信息访问和自动化铺平了道路。互联网将世界各地的人们连接在一起，使企业能够开展国际业务，学生可以获得在线教育，个人可以随时随地获取新闻信息。")
    list.add("智能手机的普及进一步推动了数字化进程。如今，手机已成为沟通、娱乐和商业的重要工具。社交媒体平台的兴起彻底改变了人们的交流方式，使信息分享更加便捷，同时也带来了隐私安全、数据泄露和数字成瘾等问题。")
    list.add("人工智能（AI）是近年来最具颠覆性的技术之一。AI 系统如今可以执行许多需要人类智慧的任务，例如图像识别、语言翻译，甚至医学诊断。机器学习算法的不断优化，使 AI 变得更加高效和精准。")
    list.add("由人工智能和机器人技术驱动的自动化正在重塑制造业、医疗和金融等行业。企业依赖自动化系统来优化流程、降低成本并提高效率。然而，自动化的发展也导致了一些工作岗位的消失，迫使劳动者不断学习新技能以适应变化。AI 在决策过程中的潜在偏见及其对就业市场的影响，已成为社会关注的重要议题。")
    list.add("教育行业也因科技的发展受益匪浅。从传统课堂教学向在线教育的转变，为学生提供了前所未有的知识获取方式。数字化学习平台，如在线课程、虚拟课堂和教育软件，使学习过程更加互动化和个性化。")
    val bookchapter = BookChapter("今夜无人入眠", list)

    var currentPage by remember { mutableStateOf(ContentPage()) }
    var currentIdx by remember { mutableIntStateOf(0) }
    BasicReadView(bookchapter.title,dataLine = currentPage, onSize = {
            size ->
        helper.updateSize(size)
        helper.layoutText(bookchapter)
        currentPage = helper.pages.getOrNull(currentIdx) ?: ContentPage()
    }) {
        currentIdx++
        currentPage = helper.pages.getOrNull(currentIdx) ?: ContentPage()
    }
}

