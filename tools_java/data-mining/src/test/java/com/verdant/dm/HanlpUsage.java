package com.verdant.dm;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.suggest.Suggester;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import org.junit.Test;

import java.util.List;

/**
 * <p>文件名称：HanlpUsage.java</p>
 * <p>文件描述：</p>
 * <p>其他说明： </p>
 * <p>版权所有： 版权所有(C)2016-2099</p>
 * <p>公   司： 新华智云 </p>
 * <p>完成日期：2017-08-29</p>
 *
 * @author yangcongyu@shuwen.com
 * @version 1.0
 */
public class HanlpUsage {
    @Test
    public void keywords() {
        String content = "程序员(英文Programmer)是从事程序开发、维护的专业人员。一般将程序员分为程序设计人员和程序编码人员，但两者的界限并不非常清楚，特别是在中国。软件从业人员分为初级程序员、高级程序员、系统分析员和项目经理四大类。";
        List<String> keywordList = HanLP.extractKeyword(content, 5);
        System.out.println(keywordList);
    }

    @Test
    public void summary() {
        String document = "新华社北京1月23日电 中共十九大后首次地方两会启幕，这一中国特色社会主义进入新时代关键时期召开的重要会议，将定调中国各地未来一年发展方向、目标与路径。人们期待政府捧出新时代地方发展蓝图。 新疆、安徽21日率先召开地方两会，其他各省区市将先后于本月月底前完成全部议程。 （小标题）守住民生底线 此前多省份2017年经济年报已密集出炉。生产总值去年首次突破万亿元的新疆，“块头”虽不抢眼，但五年年均增长9%，城镇、农村居民人均可支配收入年均分别增长10.1%、9.9%，民生支出连续多年占公共预算支出的70%等多个数据仍让人眼前一亮。 新疆维吾尔自治区主席雪克来提·扎克尔说，过去五年，居民收入增长总体高于经济增长。通过大办民生实事好事，全区各族群众的获得感、幸福感不断提升。 以往只会放牧的阿曼哈孜·朱玛哈孜，如今已是村里定居点小有名气的装修师傅。在电视上看到这段讲话，他不禁鼓起掌来。 去年，他从库鲁斯台草原上搬迁下来，定居到新疆塔城市也门勒乡沙吾林村，做起了装修工人，月收入可达四五千元，还收了学徒。“这都是政府的功劳，我们离城市近了，孩子也在城里上学了，比以前的生活好太多了。” 　图为实施乡村振兴战略“三步走”时间表。新华社记者 王永卓 编制 近年来，新疆塔城地区实施保护和治理库鲁斯台草原工程，计划实施牧民搬迁共计1436户，现已实施牧民搬迁835户。牧民定居为阿曼哈孜·朱玛哈孜的小家庭带来了从未有过的幸福感。 根据新疆两会释放的最新信息，未来将进一步加强生态保护建设，加快库鲁斯台等重大生态修复工程建设。 民生是地方两会的重要议题。从此前各地召开的经济工作会议部署可以看出，2018年各地主要目标几乎都包括了进一步加强和改善民生，织牢民生保障安全网，对接用好国家守住民生底线的社会托底政策，不断提升人民群众的获得感和幸福感等。 （小标题）六亿农民的战略机遇期 中共十九大用一个章节阐释“实施乡村振兴战略”，中央农村工作会议提出了实施乡村振兴战略的目标任务和基本原则。有着六亿人口的中国农村，在乡村振兴战略实施的大背景下，正在迎来重大战略机遇期。 专家表示，2018年中国将出台多个相关配套规划，包括土地承包期再延长30年的政策等。 40年前，中国改革尚未拉开帷幕，18户安徽小岗村农民冒死按下了“大包干”的红手印，催生了家庭联产承包制，解放了农村生产力。 作为“改革原点”的小岗村人，如今又有了新的期盼。 在改革大道的一侧，“金昌食府”正在实施扩建，这是大包干带头人严金昌经营的农家乐，近年来小岗村的红色旅游很有起色，严金昌家每天宾客盈门。 　这是2017年7月13日航拍的安徽省凤阳县小岗村近年来新建的农民新居。新华社记者 郭晨 摄 “乡村振兴”战略的部署和实施让他感到振奋。“以前为了吃饱肚子，我们搞大包干，现在要打破传统农业，搞现代农业、绿色农业、科技农业。” 大包干带头人严宏昌在吃下土地承包经营权再延长三十年的“定心丸”后，叮嘱儿子严余山要经营好葡萄种植园，继续加大投资规模。 安徽省政府工作报告提出，2018年要大力实施乡村振兴战略，推进“三农”工作全面发展，特别提出要扎实推进现代农业建设、深化农村综合改革、着力补齐农村突出短板。 （小标题）良好生态助力脱贫“摘帽” 江西省是全国三个省级生态文明试验区之一。未来5年，江西既要做大经济总量，更要在生态文明建设取得重大进展。 江西省省长刘奇1月23日在政府工作报告中提出，力争到2020年，初步建成具有江西特色、系统完整的生态文明制度体系。力争到2022年，创新型经济更加活跃，生态环境质量领先全国，形成一批可复制可推广的生态文明制度成果。 江西井冈山是中国贫困退出机制建立后首个脱贫“摘帽”的贫困县。在井冈山茅坪乡神山村，脱贫之后的乡亲们围坐在电视机前收看两会直播。 良好的生态环境让神山村成为井冈山乡村旅游的热点景区，神山村村民彭夏英希望，政府继续推进生态文明建设，用好山、好水、好空气，吸引更多游客到江西旅游，让更多百姓从良好生态中获益。 （小标题）没有水分的高质量发展 去年年底的中央经济工作会议提出，中国经济已由高速增长阶段转向高质量发展阶段。 2018年初，辽宁、内蒙古、天津多地主动曝出GDP“注水”。 捅破“泡沫”，是加速推进高质量发展的题中应有之义。剔除“虚胖”后，依据高质量数据作出的经济布局和政策部署将会更加科学有效。 天津滨海新区将2016年地区生产总值由原来的10002.31亿元调整为6654亿元，缩水幅度达三分之一。 天津市相关负责人告诫当地干部，必须从“速度情结”“换挡焦虑”中摆脱出来，从落后的发展理念中摆脱出来，从粗放的发展方式中摆脱出来，彻底甩掉单纯追求GDP增速包袱，下决心推动高质量发展。 安徽则强调保持经济社会发展速度的同时注重发展质量，明确提出2018年的预期目标：全省生产总值增长8%以上，财政收入增速高于经济增长；节能减排完成年度目标任务；并就重大科技基础设施建设提出具体构想，2018年将实施专项支持政策，加快建设先进制造业产业集群。 专家指出，当增长的动力从要素驱动转变为创新驱动，全要素生产率对经济增长将作出更大贡献，经济结构变迁将呈现出优化升级的特点。 （小标题）改革开放再出发 2018年是改革开放40周年。作为改革开放排头兵的上海市，坚定了“改革开放再出发”的信心和决心。上海将“依托洋山深水港和浦东国际机场探索建设自由贸易港”，上海市市长应勇提出，要对照国际最高标准、最好水平。 上海还将加快建设“开放型经济体系的风险压力测试区”“开放和创新融为一体的综合改革试验区”“政府治理能力的先行区”。未来五年，上海将形成具有全球影响力的科技创新中心基本框架，迈向卓越的全球城市。 “改革永远在路上，要有再出发的勇气和信念，敢为人先，敢闯敢试。唯此才能铺筑一条通往未来的改革新篇。”上海市浦东新区区委书记翁祖亮说。（执笔记者：任沁沁　参与采写：李晓玲　杨丁淼　沈洋　许晓青　杨一苗　春拉）";
        List<String> sentenceList = HanLP.extractSummary(document, 7);
        System.out.println(sentenceList);
    }

    @Test
    public void phrase() {
        String text = "算法工程师\n" +
                "算法（Algorithm）是一系列解决问题的清晰指令，也就是说，能够对一定规范的输入，在有限时间内获得所要求的输出。" +
                "如果一个算法有缺陷，或不适合于某个问题，执行这个算法将不会解决这个问题。不同的算法可能用不同的时间、" +
                "空间或效率来完成同样的任务。一个算法的优劣可以用空间复杂度与时间复杂度来衡量。算法工程师就是利用算法处理事物的人。\n" +
                "\n" +
                "1职位简介\n" +
                "算法工程师是一个非常高端的职位；\n" +
                "专业要求：计算机、电子、通信、数学等相关专业；\n" +
                "学历要求：本科及其以上的学历，大多数是硕士学历及其以上；\n" +
                "语言要求：英语要求是熟练，基本上能阅读国外专业书刊；\n" +
                "必须掌握计算机相关知识，熟练使用仿真工具MATLAB等，必须会一门编程语言。\n" +
                "\n" +
                "2研究方向\n" +
                "视频算法工程师、图像处理算法工程师、音频算法工程师 通信基带算法工程师\n" +
                "\n" +
                "3目前国内外状况\n" +
                "目前国内从事算法研究的工程师不少，但是高级算法工程师却很少，是一个非常紧缺的专业工程师。" +
                "算法工程师根据研究领域来分主要有音频/视频算法处理、图像技术方面的二维信息算法处理和通信物理层、" +
                "雷达信号处理、生物医学信号处理等领域的一维信息算法处理。\n" +
                "在计算机音视频和图形图像技术等二维信息算法处理方面目前比较先进的视频处理算法：机器视觉成为此类算法研究的核心；" +
                "另外还有2D转3D算法(2D-to-3D conversion)，去隔行算法(de-interlacing)，运动估计运动补偿算法" +
                "(Motion estimation/Motion Compensation)，去噪算法(Noise Reduction)，缩放算法(scaling)，" +
                "锐化处理算法(Sharpness)，超分辨率算法(Super Resolution),手势识别(gesture recognition),人脸识别(face recognition)。\n" +
                "在通信物理层等一维信息领域目前常用的算法：无线领域的RRM、RTT，传送领域的调制解调、信道均衡、信号检测、网络优化、信号分解等。\n" +
                "另外数据挖掘、互联网搜索算法也成为当今的热门方向。\n" +
                "算法工程师逐渐往人工智能方向发展。";
        List<String> phraseList = HanLP.extractPhrase(text, 5);
        System.out.println(phraseList);
    }


    @Test
    public void suggester() {
        Suggester suggester = new Suggester();
        String[] titleArray =
                (
                        "威廉王子发表演说 呼吁保护野生动物\n" +
                                "《时代》年度人物最终入围名单出炉 普京马云入选\n" +
                                "“黑格比”横扫菲：菲吸取“海燕”经验及早疏散\n" +
                                "日本保密法将正式生效 日媒指其损害国民知情权\n" +
                                "英报告说空气污染带来“公共健康危机”"
                ).split("\\n");
        for (String title : titleArray) {
            suggester.addSentence(title);
        }
        System.out.println(suggester.suggest("发言", 1));       // 语义
        System.out.println(suggester.suggest("危机公共", 1));   // 字符
        System.out.println(suggester.suggest("mayun", 1));      // 拼音
    }

    @Test
    public void sentence() {
        CoNLLSentence sentence = HanLP.parseDependency("徐先生还具体帮助他确定了把画雄鹰、松鼠和麻雀作为主攻目标。");
        System.out.println(sentence);
        // 可以方便地遍历它
        for (CoNLLWord word : sentence) {
            System.out.printf("%s --(%s)--> %s\n", word.LEMMA, word.DEPREL, word.HEAD.LEMMA);
        }
        // 也可以直接拿到数组，任意顺序或逆序遍历
        CoNLLWord[] wordArray = sentence.getWordArray();
        for (int i = wordArray.length - 1; i >= 0; i--) {
            CoNLLWord word = wordArray[i];
            System.out.printf("%s --(%s)--> %s\n", word.LEMMA, word.DEPREL, word.HEAD.LEMMA);
        }
        // 还可以直接遍历子树，从某棵子树的某个节点一路遍历到虚根
        CoNLLWord head = wordArray[12];
        while ((head = head.HEAD) != null) {
            if (head == CoNLLWord.ROOT) System.out.println(head.LEMMA);
            else System.out.printf("%s --(%s)--> ", head.LEMMA, head.DEPREL);
        }
    }
}