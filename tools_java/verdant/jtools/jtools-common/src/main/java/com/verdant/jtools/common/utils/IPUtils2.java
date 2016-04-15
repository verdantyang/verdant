package com.verdant.jtools.common.utils;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Author: verdant
 * Create: 2016/1/28
 * Func:   IP工具类
 * Desc:   对操作的目标IP进行合法性校验，以及对目标IP进行在用户所属的IP范围内校验
 *
 * Handle two IPRange (public)
 * validateIPEqual             校验两个IP地址段是否一致
 * checkIPRangeIntersected     校验一组IP段之间是否有交集
 * validateIPRange             校验IP地址范围是否有交集（保留旧有的方法名）
 * checkIPRangeIncluded        判断一组IP段是否完全包含了另一IP段
 * getIPRangeIntersection      取两个IP段之间的交集
 * getIPRangeDisjoint          求出要校验的IP地址段中不在源IP地址段的部分
 *
 * Handle one IPRange (public)
 * getNetworkAddress           计算网络地址
 * getBeginAddress             计算第一个ip地址，并将其转换为long型数字
 * getEndAddress               计算最后一个IP地址，并将其转换为long型数字
 * getRangeIPCount             求IP地址段内的地址数量
 * concatIPRanges              将离散的、无序的IP地址段拼接成从小到大排序的连续的IP地址段
 * changeIPRangesToSubNets     将一组IP地址段拼接，并按起始IP从小到大排序，再转换成点分十进制IPv4 点分十进制反掩码的表示方式
 * changeIPRangesWithAntiMask  将一组IP地址段拼接，并按起始IP从小到大排序，再转换成点分十进制IPv4 点分十进制反掩码的表示方式
 * calculateSubNets            根据骨干路由拆分IP网段子网，只有当源IP子网掩码位数大于等于24位且小于等于拆分的子网掩码位数时才进行拆分
 * getBeginSubnetAddress       根据IP地址(点分十进制)、子网掩码获得子网起始地址
 * getEndSubnetAddress         根据子网地址(十进制)、子网掩码位数计算网段最后的IP地址
 * getIpList                   取出IP地址段中的所有IP
 * getIpListByIpMask           根据Ip地址和子网掩码位数  查询所有该网段的地址
 *
 * About mask (public)
 * getMask                    根据掩码位数计算掩码
 * getAntiMask                计算反掩码
 * getSubnetSize              根据掩码位数计算子网大小
 * subMaskToSubNum            转换为掩码位数
 *
 * About long type IP (public)
 * longToIP                   将10进制整数形式转换成127.0.0.1形式的IP地址
 * ipToLong                   将127.0.0.1 形式的IP地址转换成10进制整数
 *
 *
 * About long type IP (private)
 * longRangeToIPRange         将一个数值型表示的IP地址段，转换成字符串表示的IP地址段
 * longRangesToIPRanges       将数值表示的IP地址段拼接在一起，并转成字符串表示的IP地址段
 * longRangesToSubNets        将数值表示的IP地址段拼接在一起，并转子网地址和子网掩码位数列表
 * ipRangesToLongRanges       将一组IP地址段转换成数值型起止IP数组表示的IP地址段
 * concatLongIPRanges         将数值表示的IP地址段拼接在一起。即将无序的、散碎的IP地址或IP地址段连接成从小到大有序的，连续的IP地址段
 * checkLongIPRange           验证long型IP地址段起始IP必需小于等于endIP，如不满足则抛出运行时异常
 *
 * About mask (private)
 * getSubnetMaskSizeFromIP    根据IP计算子网掩码位数
 * getSubnetMaskSizeFromMask  根据掩码返回掩码位数
 * splitIPAndMask             拆分出IP和子网掩码
 * checkSubnetMaskSize        验证掩码位数有效性，掩码位数>=1 且 <=32 为有效，否则抛异常
 */
public class IPUtils2 {
    private static final Pattern IP_ADDRESS = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    /**
     * 检查 IP地址是否是 合法的
     *
     * @param ip
     * @return
     */
    public static boolean checkValid(String ip) {
        return IP_ADDRESS.matcher(ip).matches();
    }
    /**
     * 校验两个IP地址段是否一致
     *
     * @param sourceIPRange IP地址段，多段之间用逗号分隔，每段接受点分十进制IPv4、IPv4 掩码、IPv4/掩码位数、IPv4-IPv4片段的四种格式
     * @param targetIPRange IP地址段，多段之间用逗号分隔，每段接受点分十进制IPv4、IPv4 掩码、IPv4/掩码位数、IPv4-IPv4片段的四种格式
     * @return 一致则返回true，否则为false
     */
    public static boolean validateIPEqual(String sourceIPRange, String targetIPRange) {
        if (sourceIPRange.contains(",") || targetIPRange.contains(",")) {
            /* IP地址段字符串中有多个用逗号分隔的IP段，则采用以下比较方法：
            * targetIPRange是sourceIPRange的子集，同时sourceIPRange也是targetIPRange的子集，则两者完全相等
            */
            String[] sourceIPRanges = sourceIPRange.split(",");
            String[] targetIPRanges = targetIPRange.split(",");
            return checkIPRangeIncluded(sourceIPRanges, targetIPRanges)
                    && checkIPRangeIncluded(targetIPRanges, sourceIPRanges);
        } else {
            //进行比较的都是单一的IP地址段，直接比较两者的起始和截止地址
            return getBeginAddress(sourceIPRange) == getBeginAddress(targetIPRange)
                    && getEndAddress(sourceIPRange) == getEndAddress(targetIPRange);
        }
    }

    /**
     * 校验一组IP段之间是否有交集
     *
     * @param ipRanges IP地址段，接受点分十进制IPv4、IPv4 掩码、IPv4/掩码位数、IPv4-IPv4片段四种格式
     * @return true：有交集，false：无交集
     */
    public static boolean checkIPRangeIntersected(String... ipRanges) {
        //求出IP地址段的起始和截止IP
        long[][] longIPRanges = ipRangesToLongRanges(ipRanges).toArray(new long[ipRanges.length][]);
        ;

        //比较是否有交集
        for (int i = 0; i < ipRanges.length; i++) {
            for (int j = i + 1; j < ipRanges.length; j++) {
                if (longIPRanges[i][0] <= longIPRanges[j][1]
                        && longIPRanges[i][1] >= longIPRanges[j][0]) {
                    //A的起始地址小于等于B的截止地址并且A的截止地址大于等于B的起始地址，则有交集
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 校验一组IP段之间是否有交集
     *
     * @param ipRanges IP地址段，接受点分十进制IPv4、IPv4 掩码、IPv4/掩码位数、IPv4-IPv4片段四种格式
     * @return true：有交集，false：无交集
     */
    public static boolean checkIPRangeIntersected(Collection<String> ipRanges) {
        return checkIPRangeIntersected(ipRanges.toArray(new String[ipRanges.size()]));
    }

    /**
     * 判断一组IP段是否完全包含了另一IP段
     *
     * @param sourceRanges 全集IP地址段
     * @param toCheckRange 待校验IP地址段
     * @return 当toCheckRange为sourceRanges的子集时为true，否则为false
     */
    public static boolean checkIPRangeIncluded(Collection<String> sourceRanges, String toCheckRange) {
        String[] empty = new String[0];
        return checkIPRangeIncluded(sourceRanges.toArray(empty), toCheckRange);
    }

    /**
     * 判断一组IP段是否完全包含了另一IP段
     *
     * @param sourceRanges 全集IP地址段
     * @param toCheckRange 待校验IP地址段
     * @return 当toCheckRange为sourceRanges的子集时为true，否则为false
     */
    public static boolean checkIPRangeIncluded(String[] sourceRanges, String toCheckRange) {
        return checkIPRangeIncluded(sourceRanges, new String[]{toCheckRange});
    }

    /**
     * 判断一组IP段是否完全包含了另一IP段
     *
     * @param sourceRanges  全集IP地址段
     * @param toCheckRanges 待校验IP地址段
     * @return 当toCheckRange为sourceRanges的子集时为true，否则为false
     */
    public static boolean checkIPRangeIncluded(Collection<String> sourceRanges, Collection<String> toCheckRanges) {
        String[] empty = new String[0];
        return checkIPRangeIncluded(sourceRanges.toArray(empty), toCheckRanges.toArray(empty));
    }

    /**
     * 判断一组IP段是否完全包含了另一IP段
     *
     * @param sourceRanges  源IP地址段，IP地址段接受点分十进制IPv4、IPv4 掩码、IPv4/掩码位数、IPv4-IPv4片段四种格式
     * @param toCheckRanges 待校验IP地址段，IP地址段接受点分十进制IPv4、IPv4 掩码、IPv4/掩码位数、IPv4-IPv4片段四种格式
     * @return 当toCheckRanges为sourceRanges的子集时为true，否则为false
     */
    public static boolean checkIPRangeIncluded(String[] sourceRanges, String[] toCheckRanges) {
        //待校验IP地址段的起止IP的数值表示列表
        LinkedList<long[]> toCheckList = new LinkedList<>();
        //源IP地址段的起止IP的数值表示列表
        LinkedList<long[]> sourceList = new LinkedList<>();
        //暂存截止IP
        long toCheckEndIP = 0L;
        //标志是否需要创建一个新的数组来暂存起止IP
        boolean createNewStartEndIPArrayFlag = false;
        //暂存待校验起止IP
        long[] toCheckStartEndIP = null;
        //暂存源起止IP
        long[] sourceStartEndIP = null;
        //待校验IP段数组当前检索下标
        int toCheckIndex = 0;
        //源IP段数组当前检索下标
        int sourceIndex = 0;
        //缓存是否包含的临时比较结果
        boolean included = false;

        //逐个检索待校验的IP段，并与源IP段逐个比较，当出现第一个不能被完全包含的待校验IP段时即返回false
        for (ListIterator<long[]> toCheckIter = toCheckList.listIterator(); ; ) {
            //逐个从待校验IP段数组中取出IP段存入ListIterator中，以待比较
            if (!toCheckIter.hasNext()) {
                if (toCheckIndex == toCheckRanges.length) {
                    break;
                }
                toCheckIter.add(new long[]{getBeginAddress(toCheckRanges[toCheckIndex]), getEndAddress(toCheckRanges[toCheckIndex])});
                toCheckIndex++;
                toCheckIter.previous();
            }
            toCheckStartEndIP = toCheckIter.next();
            //比较一个新的待校验IP段时置标志为false
            included = false;

            for (ListIterator<long[]> sourceIter = sourceList.listIterator(); !included; ) {
                //逐个从源IP段数组中取出IP段存入ListIterator中，以待比较
                if (!sourceIter.hasNext()) {
                    if (sourceIndex == sourceRanges.length) {
                        break;
                    }
                    sourceIter.add(new long[]{getBeginAddress(sourceRanges[sourceIndex]), getEndAddress(sourceRanges[sourceIndex])});
                    sourceIndex++;
                    sourceIter.previous();
                }
                sourceStartEndIP = sourceIter.next();

                if (toCheckStartEndIP[0] > sourceStartEndIP[1] || toCheckStartEndIP[1] < sourceStartEndIP[0]) {
                    //当前比较的两个IP段之间没有交集
                    continue;
                }

                //否则，当前的sourceStartEndIP包含了部分（或全部）toCheckStartEndIP的IP地址
                included = true;

                //把toCheckStartEndIP中被当前sourceStartEndIP覆盖的部分扣除，剩余部分放入toCheckIter，以待下一轮循环检测
                toCheckEndIP = toCheckStartEndIP[1];
                createNewStartEndIPArrayFlag = false;
                if (sourceStartEndIP[0] > toCheckStartEndIP[0]) {
                    //toCheckStartEndIP的低位部分有剩余
                    toCheckStartEndIP[1] = sourceStartEndIP[0] - 1;
                    //将指针移回toCheckStartEndIP起始位置
                    toCheckIter.previous();
                    //如果高位部分有剩余，则需要新建一个数组保存高位剩余部分
                    createNewStartEndIPArrayFlag = true;
                }
                if (sourceStartEndIP[1] < toCheckEndIP) {
                    //toCheckStartEndIP的高位部分有剩余
                    if (createNewStartEndIPArrayFlag) {
                        //高位剩余部分需要创建一个新的数组来保存
                        toCheckStartEndIP = new long[]{0L, toCheckEndIP};
                        toCheckIter.add(toCheckStartEndIP);
                    }
                    toCheckStartEndIP[0] = sourceStartEndIP[1] + 1;
                    //将指针移回toCheckStartEndIP起始位置
                    toCheckIter.previous();
                }
            }

            if (!included) {
                //找到第一个不能被完全包含的IP段时即返回false
                return false;
            }
        }

        //所有IP段都通过校验，则表示被完全包含
        return true;
    }

    /**
     * 取两个IP段之间的交集
     *
     * @param ranges1 第一个IP地址段，接受点分十进制IPv4、IPv4 掩码、IPv4/掩码位数、IPv4-IPv4片段四种格式
     * @param ranges2 第二个IP地址段，接受点分十进制IPv4、IPv4 掩码、IPv4/掩码位数、IPv4-IPv4片段四种格式
     * @return 交集IP地址段的字符串表示
     */
    public static List<String> getIPRangeIntersection(Collection<String> ranges1, Collection<String> ranges2) {
        return getIPRangeIntersection(
                ranges1.toArray(new String[ranges1.size()]),
                ranges2.toArray(new String[ranges2.size()])
        );
    }

    /**
     * 取两个IP段之间的交集
     *
     * @param ranges1 第一个IP地址段，接受点分十进制IPv4、IPv4 掩码、IPv4/掩码位数、IPv4-IPv4片段四种格式
     * @param ranges2 第二个IP地址段，接受点分十进制IPv4、IPv4 掩码、IPv4/掩码位数、IPv4-IPv4片段四种格式
     * @return 交集IP地址段的字符串表示
     */
    public static List<String> getIPRangeIntersection(String[] ranges1, String[] ranges2) {
        //先将两个IP地址段都转换成long型的起始IP、截止IP数组表示形式
        List<long[]> ipBigRange = ipRangesToLongRanges(ranges1);
        List<long[]> ipSmallRange = ipRangesToLongRanges(ranges2);

        //暂存数值型的交集结果
        LinkedList<long[]> longResult = new LinkedList<>();
        //暂存相交部分
        long[] intersection = null;
        for (long[] rangeBig : ipBigRange) {
            //对rangeBig里的每个IP地址段，判断与rangeSmall里的所有IP的相交部分
            for (long[] rangeSmall : ipSmallRange) {
                if (rangeBig[0] <= rangeSmall[1] && rangeBig[1] >= rangeSmall[0]) {
                    //有相交部分，取出相交部分
                    intersection = new long[]{
                            Math.max(rangeBig[0], rangeSmall[0]),
                            Math.min(rangeBig[1], rangeSmall[1])
                    };
                    longResult.add(intersection);
                }
            }
            //至此已求出当前rangeBig地址段与所有的ipSmallRange中的地址段之间的交集，将结果拼接成连续的IP地址段
            concatLongIPRanges(longResult);
        }

        //循环完毕，已求出所有相交部分，将结果转成字符串形式的IP地址段
        return longRangesToIPRanges(longResult);
    }

    /**
     * 求出要校验的IP地址段中不在源IP地址段的部分
     *
     * @param sourceRanges  源IP地址段，接受点分十进制IPv4、IPv4 掩码、IPv4/掩码位数、IPv4-IPv4片段四种格式
     * @param toCheckRanges 要校验的IP地址段，接受点分十进制IPv4、IPv4 掩码、IPv4/掩码位数、IPv4-IPv4片段四种格式
     * @return 要校验的IP地址段不在源IP地址段中的部分
     */
    public static List<String> getIPRangeDisjoint(Collection<String> sourceRanges, Collection<String> toCheckRanges) {
        return getIPRangeDisjoint(
                sourceRanges.toArray(new String[sourceRanges.size()]),
                toCheckRanges.toArray(new String[toCheckRanges.size()])
        );
    }

    /**
     * 求出要校验的IP地址段中不在源IP地址段的部分
     *
     * @param sourceRanges  源IP地址段，接受点分十进制IPv4、IPv4 掩码、IPv4/掩码位数、IPv4-IPv4片段四种格式
     * @param toCheckRanges 要校验的IP地址段，接受点分十进制IPv4、IPv4 掩码、IPv4/掩码位数、IPv4-IPv4片段四种格式
     * @return 要校验的IP地址段不在源IP地址段中的部分
     */
    public static List<String> getIPRangeDisjoint(String[] sourceRanges, String[] toCheckRanges) {
        //暂存数值型的被校验IP地址段
        LinkedList<long[]> toCheckList = new LinkedList<>();
        //被校验的IP地址段数组当前检索下标
        int toCheckIndex = 0;
        //暂存数值型的源IP地址段
        LinkedList<long[]> sourceList = new LinkedList<>();
        //源IP地址段数组当前检索下标
        int sourceIndex = 0;
        //暂存被校验IP地址段
        long[] toCheckStartEndIP = null;
        //暂存源IP地址段
        long[] sourceStartEndIP = null;
        //暂存IP
        long tmpLongIP = 0;

        //逐个检索待校验的IP地址段，将之与所有源IP地址段比较扣除相交部分
        for (ListIterator<long[]> toCheckIter = toCheckList.listIterator(); ; ) {
            //逐个检索待校验的IP地址段数组，将地址段加入ListIterator中
            if (!toCheckIter.hasNext()) {
                if (toCheckIndex == toCheckRanges.length) {
                    break;
                }
                toCheckIter.add(new long[]{getBeginAddress(toCheckRanges[toCheckIndex]), getEndAddress(toCheckRanges[toCheckIndex])});
                toCheckIndex++;
                toCheckIter.previous();
            }
            toCheckStartEndIP = toCheckIter.next();

            for (ListIterator<long[]> sourceIter = sourceList.listIterator(); ; ) {
                //逐个检索源IP地址段数组，将地址段加入ListIterator中
                if (!sourceIter.hasNext()) {
                    if (sourceIndex == sourceRanges.length) {
                        break;
                    }
                    sourceIter.add(new long[]{getBeginAddress(sourceRanges[sourceIndex]), getEndAddress(sourceRanges[sourceIndex])});
                    sourceIndex++;
                    sourceIter.previous();
                }
                sourceStartEndIP = sourceIter.next();

                //如果有交集，从toCheckStartEndIP中扣除相交部分
                if (toCheckStartEndIP[0] < sourceStartEndIP[0]
                        && toCheckStartEndIP[1] <= sourceStartEndIP[1]
                        && toCheckStartEndIP[1] >= sourceStartEndIP[0]) {
                    //toCheckStartEndIP剩下低位部分，剩余部分需要再与其他源IP地址段比较
                    toCheckStartEndIP[1] = sourceStartEndIP[0] - 1;
                } else if (toCheckStartEndIP[0] >= sourceStartEndIP[0] && toCheckStartEndIP[1] <= sourceStartEndIP[1]) {
                    //toCheckStartEndIP不剩，将之从toCheckIter中删除
                    toCheckIter.remove();
                } else if (toCheckStartEndIP[0] >= sourceStartEndIP[0]
                        && toCheckStartEndIP[0] <= sourceStartEndIP[1]
                        && toCheckStartEndIP[1] > sourceStartEndIP[1]) {
                    //toCheckStartEndIP剩下高位部分，剩余部分需要再与其他源IP地址段比较
                    toCheckStartEndIP[0] = sourceStartEndIP[1] + 1;
                } else if (toCheckStartEndIP[0] < sourceStartEndIP[0] && toCheckStartEndIP[1] > sourceStartEndIP[1]) {
                    //toCheckStartEndIP中间部分被扣除，两端有剩余，剩余部分需要再与其他源IP地址段比较
                    tmpLongIP = toCheckStartEndIP[1];
                    toCheckStartEndIP[1] = sourceStartEndIP[0] - 1;
                    //添加后面高位剩余部分
                    toCheckIter.add(new long[]{sourceStartEndIP[1] + 1, tmpLongIP});
                    //指针向前移一位，移到新加的高位数组处
                    toCheckIter.previous();
                    //指针再向前移一位，移到toCheckStartEndIP剩余的低位部分处
                    toCheckIter.previous();
                }
                //else   //不相交不处理
            }
        }

        //至此toCheckList中为不相交部分，再将数值型IP地址段转成字符串型IP地址段
        return longRangesToIPRanges(toCheckList);
    }

    /**
     * 将离散的、无序的IP地址段拼接成从小到大排序的连续的IP地址段
     *
     * @param ipRanges 源IP地址段，接受点分十进制IPv4、IPv4 掩码、IPv4/掩码位数、IPv4-IPv4片段四种格式
     * @return 拼接后的有序连续的IP地址段
     */
    public static List<String> concatIPRanges(List<String> ipRanges) {
        return concatIPRanges(ipRanges.toArray(new String[ipRanges.size()]));
    }

    /**
     * 将离散的、无序的IP地址段拼接成从小到大排序的连续的IP地址段
     *
     * @param ipRanges 源IP地址段，接受点分十进制IPv4、IPv4 掩码、IPv4/掩码位数、IPv4-IPv4片段四种格式
     * @return 拼接后的有序连续的IP地址段
     */
    public static List<String> concatIPRanges(String... ipRanges) {
        //先将字符串表示的IP地址段转换成数值型IP地址段
        List<long[]> longIPRanges = ipRangesToLongRanges(ipRanges);

        //拼接地址段并转换成字符串型地址段
        return longRangesToIPRanges(longIPRanges);
    }

    /**
     * 求IP地址段内的地址数量
     *
     * @param ipRanges 源IP地址段，接受点分十进制IPv4、IPv4 掩码、IPv4/掩码位数、IPv4-IPv4片段四种格式
     * @return 非重复IP记数
     */
    public static long getRangeIPCount(String... ipRanges){
        //先将字符串表示的IP地址段转换成数值型IP地址段
        List<long[]> longIPRanges = ipRangesToLongRanges(ipRanges);

        //先将原始IP地址段有序拼接成连续的IP地址段
        LinkedList<long[]> sortedLongRanges = new LinkedList<>(longIPRanges);
        concatLongIPRanges(sortedLongRanges);

        long ipCount = 0L;
        for (long[] range : sortedLongRanges) {
            ipCount += (range[1] - range[0] + 1);
        }

        return ipCount;
    }

    /**
     * 将一组IP地址段拼接，并按起始IP从小到大排序，再转换成点分十进制IPv4/掩码位数或IPv4 点分十进制掩码的表示方式
     *
     * @param showMaskSize 为true时返回的格式为：点分十进制IPv4/掩码位数，否则为IPv4 点分十进制掩码
     * @param ipRanges     待转换的IP地址范围
     * @return 转换后的IP地址段
     */
    public static List<String> changeIPRangesToSubNets(boolean showMaskSize, String... ipRanges) {
        return changeIPRangesToSubNets(1, showMaskSize, ipRanges);
    }

    /**
     * 将一组IP地址段拼接，并按起始IP从小到大排序，再转换成点分十进制IPv4/掩码位数或IPv4 点分十进制掩码的表示方式
     *
     * @param minMaskSize  允许的最小的掩码位数
     * @param showMaskSize 为true时返回的格式为：点分十进制IPv4/掩码位数，否则为IPv4 点分十进制掩码
     * @param ipRanges     待转换的IP地址范围
     * @return 转换后的IP地址段
     */
    public static List<String> changeIPRangesToSubNets(int minMaskSize, boolean showMaskSize, String... ipRanges) {
        //先将字符串型IP地址段转换成数值型起止IP数组
        List<long[]> cacheList = ipRangesToLongRanges(ipRanges);

        //将起止IP地址转换成子网地址和子网掩码位数形式
        cacheList = longRangesToSubNets(cacheList, minMaskSize);

        //用于缓存子网字符串表达式
        StringBuilder buf = new StringBuilder(32);

        //保存最终结果
        List<String> resultList = new ArrayList<>(cacheList.size());

        if (showMaskSize) {
            //生成 IPv4/掩码位数 形式
            for (long[] sub : cacheList) {
                buf.replace(0, buf.length(), longToIP(sub[0]))
                        .append("/")
                        .append(sub[1]);
                resultList.add(buf.toString());
            }
        } else {
            //生成 IPv4 掩码 形式
            for (long[] sub : cacheList) {
                buf.replace(0, buf.length(), longToIP(sub[0]))
                        .append(" ")
                        .append(getMask((int) sub[1]));
                resultList.add(buf.toString());
            }
        }

        return resultList;
    }

    /**
     * 将一组IP地址段拼接，并按起始IP从小到大排序，再转换成点分十进制IPv4 点分十进制反掩码的表示方式
     *
     * @param ipRanges 待转换的IP地址范围
     * @return 转换后的结果
     */
    public static List<String> changeIPRangesWithAntiMask(String... ipRanges) {
        return changeIPRangesWithAntiMask(1, ipRanges);
    }

    /**
     * 将一组IP地址段拼接，并按起始IP从小到大排序，再转换成点分十进制IPv4 点分十进制反掩码的表示方式
     *
     * @param minMaskSize 允许的最小掩码位数
     * @param ipRanges    待转换的IP地址范围
     * @return 转换后的结果
     */
    public static List<String> changeIPRangesWithAntiMask(int minMaskSize, String... ipRanges) {
        //先将字符串型IP地址段转换成数值型起止IP数组
        List<long[]> cacheList = ipRangesToLongRanges(ipRanges);

        //将起止IP地址转换成子网地址和子网掩码位数形式
        cacheList = longRangesToSubNets(cacheList, minMaskSize);

        //用于缓存子网字符串表达式
        StringBuilder buf = new StringBuilder(32);

        //保存最终结果
        List<String> resultList = new ArrayList<>(cacheList.size());

        //生成 IPv4 反掩码 形式
        for (long[] sub : cacheList) {
            buf.replace(0, buf.length(), longToIP(sub[0]))
                    .append(" ")
                    .append(getAntiMask((int) sub[1]));
            resultList.add(buf.toString());
        }

        return resultList;
    }

    /**
     * 校验IP地址范围是否有交集（保留旧有的方法名）
     *
     * @param sourceIP IP范围，接受点分十进制IPv4、IPv4 掩码、IPv4/掩码位数、IPv4-IPv4片段四种格式
     * @param targetIP 待校验的IP范围，接受点分十进制IPv4、IPv4 掩码、IPv4/掩码位数、IPv4-IPv4片段四种格式
     * @return 有交集返回true，否则返回false
     */
    public static boolean validateIPRange(String sourceIP, String targetIP) {
        return checkIPRangeIntersected(sourceIP, targetIP);
    }

    /**
     * 根据骨干路由拆分IP网段子网，只有当源IP子网掩码位数大于等于24位且小于等于拆分的子网掩码位数时才进行拆分
     *
     * @param sourceIP 源IP，接受点分十进制IPv4、IPv4 掩码、IPv4/掩码位数三种格式
     * @param route    拆分的子网掩码位数
     * @return 拆分后的子网网段信息，以逗号分隔，每个子网以点分十进制IPv4 点分十进制掩码的形式表示。未拆分时返回null
     */
    public static String calculateSubNets(String sourceIP, int route) {

        int subnet = getSubnetMaskSizeFromIP(sourceIP);
        if (subnet == 0) {
            return null;
        }
        if (subnet >= 24 && subnet <= route) {
            // 只有封堵解封掩24以上的IP段,并且小于骨干路由时进行拆分
            int newSubnet = ++route;
            if (newSubnet > 32) {
                newSubnet = 32;
            }
            // 分成的子网的个数
            int ipRanges = (int) Math.pow(2, newSubnet - subnet);
            int poolMax = (int) getSubnetSize(subnet);
            int ss = poolMax / ipRanges;

            String networkAddress = getNetworkAddress(sourceIP);
            int lastNum = Integer.valueOf(networkAddress.split("\\.")[3]);

            String[] splits = sourceIP.split("/")[0].split("\\.");
//            List<String> subnets = new ArrayList<String>();
            StringBuilder buf = new StringBuilder(ipRanges * 32);
            for (int i = 0; i < ipRanges; i++) {
//                subnets.add(splits[0] + "." + splits[1] + "." + splits[2] + "." + (i * ss + lastNum) + " " + getMask(newSubnet));
                buf.append(",")
                        .append(splits[0]).append(".")
                        .append(splits[1]).append(".")
                        .append(splits[2]).append(".")
                        .append(i * ss + lastNum).append(" ")
                        .append(getMask(newSubnet));
            }
            if (buf.length() > 0){
                return buf.substring(1);
            }
//            return StringUtils.join(subnets, ",");
        }
        return null;
    }

    /**
     * 计算网络地址
     *
     * @param ip 接受点分十进制IPv4 或者 IPv4 掩码 或者 IPv4/掩码位数 三种格式，无掩码也无掩码位数的默认为32位掩码
     * @return 网络地址（即子网起始IP）
     */
    public static String getNetworkAddress(String ip) {
        //接受IP、IP 掩码、IP/掩码位数三种格式
        return longToIP(getBeginAddress(ip));
    }

    /**
     * 计算网络地址
     *
     * @param ip   点分十进制表示的IPv4地址
     * @param mask 点分十进制表示的子网掩码或十进制数值表示的子网掩码位数
     * @return 网络地址（即子网起始IP）
     */
    public static String getNetworkAddress(String ip, String mask) {
        return longToIP(getBeginAddress(ip + "/" + mask));
    }

    /**
     * 根据掩码位数计算掩码
     *
     * @param masks 掩码位数
     * @return 点分十进制表示的掩码
     */
    public static String getMask(int masks) {
        if (masks == 1)
            return "128.0.0.0";
        else if (masks == 2)
            return "192.0.0.0";
        else if (masks == 3)
            return "224.0.0.0";
        else if (masks == 4)
            return "240.0.0.0";
        else if (masks == 5)
            return "248.0.0.0";
        else if (masks == 6)
            return "252.0.0.0";
        else if (masks == 7)
            return "254.0.0.0";
        else if (masks == 8)
            return "255.0.0.0";
        else if (masks == 9)
            return "255.128.0.0";
        else if (masks == 10)
            return "255.192.0.0";
        else if (masks == 11)
            return "255.224.0.0";
        else if (masks == 12)
            return "255.240.0.0";
        else if (masks == 13)
            return "255.248.0.0";
        else if (masks == 14)
            return "255.252.0.0";
        else if (masks == 15)
            return "255.254.0.0";
        else if (masks == 16)
            return "255.255.0.0";
        else if (masks == 17)
            return "255.255.128.0";
        else if (masks == 18)
            return "255.255.192.0";
        else if (masks == 19)
            return "255.255.224.0";
        else if (masks == 20)
            return "255.255.240.0";
        else if (masks == 21)
            return "255.255.248.0";
        else if (masks == 22)
            return "255.255.252.0";
        else if (masks == 23)
            return "255.255.254.0";
        else if (masks == 24)
            return "255.255.255.0";
        else if (masks == 25)
            return "255.255.255.128";
        else if (masks == 26)
            return "255.255.255.192";
        else if (masks == 27)
            return "255.255.255.224";
        else if (masks == 28)
            return "255.255.255.240";
        else if (masks == 29)
            return "255.255.255.248";
        else if (masks == 30)
            return "255.255.255.252";
        else if (masks == 31)
            return "255.255.255.254";
        else if (masks == 32)
            return "255.255.255.255";
        return "";
    }

    /**
     * 根据掩码位数计算子网大小
     *
     * @param mask 掩码数位
     * @return 子网IP数量
     */
    public static long getSubnetSize(int mask) {
        if (mask <= 0 || mask >= 32) {
            return 0;
        }
        int bits = 32 - mask;
        long size = 1;
        return size << bits;
    }

    /**
     * 计算第一个ip地址，并将其转换为long型数字
     *
     * @param ipRange IP地址段，接受点分十进制IPv4 或者 IPv4 掩码 或者 IPv4/掩码位数 或者 IPv4-IPv4片段 四种格式，无掩码也无掩码位数的默认为32位掩码
     * @return 起始IP的long型数值
     */
    public static long getBeginAddress(String ipRange) {
        if (ipRange.contains("-")) {
            // '-'隔开的地址段
            return ipToLong(ipRange.split("-")[0]);
        } else {
            String[] rangeParts = splitIPAndMask(ipRange);
            int maskSize = getSubnetMaskSizeFromMask(rangeParts[1]);
            long longIP = ipToLong(rangeParts[0]);
            checkSubnetMaskSize(maskSize);
            return (-1 << (32 - maskSize)) & longIP;
        }
    }

    /**
     * 计算最后一位IP地址，将其转换为long型数字
     *
     * @param ipRange IP地址段，接受点分十进制IPv4 或者 IPv4 掩码 或者 IPv4/掩码位数 或者 IPv4-IPv4片段 四种格式，无掩码也无掩码位数的默认为32位掩码
     * @return 最后一位IP的long型数值
     */
    public static long getEndAddress(String ipRange) {
        String[] rangeParts = null;
        if (ipRange.contains("-")) {
            rangeParts = ipRange.split("-");
            String[] ipParts1 = rangeParts[0].split("\\.");
            long startIP = ipToLong(ipParts1);
            String[] ipParts2 = rangeParts[1].split("\\.");
            for (int i = ipParts2.length - 1; i >= 0; i--) {
                ipParts1[i + ipParts1.length - ipParts2.length] = ipParts2[i];
            }
            long endIP = ipToLong(ipParts1);
            if (endIP < startIP) {
                throw new RuntimeException("Invalid IPv4 address range");
            }
            return endIP;
        } else {
            rangeParts = splitIPAndMask(ipRange);
            int maskSize = getSubnetMaskSizeFromMask(rangeParts[1]);

            checkSubnetMaskSize(maskSize);
            //得到long型IP值
            long longIP = ipToLong(rangeParts[0]);
            //计算网段地址
            longIP = longIP & (-1 << (32 - maskSize));

            //计算网段最后IP
            if (maskSize < 32) {
                longIP = longIP | (-1 >>> maskSize);
            }

            return longIP;
        }
    }

    /**
     * 根据IP地址(点分十进制)、子网掩码获得子网起始地址
     *
     * @param num       掩码位数
     * @param ipAddress IP地址(点分十进制)
     * @return String 点分十进制子网起始地址
     */
    public static String getBeginSubnetAddress(int num, String ipAddress) {
        long longIP = ipToLong(ipAddress);
        checkSubnetMaskSize(num);
        longIP = (-1 << (32 - num)) & longIP;
        return longToIP(longIP);
    }

    /**
     * 根据二进制IP地址、子网掩码 获得十进制起始地址
     *
     * @param ipChars   二进制IP地址
     * @param maskChars 二进制子网掩码
     * @return String 点分十进制子网掩码
     * @deprecated 可将点分十进制IPv4 点分十进制掩码组成的字符串传入getBeginAddress方法获取子网起始IP
     */
    public static String getBeginSubnetAddress(char[] ipChars, char[] maskChars) {
        StringBuilder sb = new StringBuilder("");
        int value = 0;
        int length = (ipChars.length > maskChars.length) ? maskChars.length : ipChars.length;
        int i = 0;
        int j = Math.abs(ipChars.length - maskChars.length);
        int chZero = (int) '0';
        for (; i < length; i++, j++) {
            //value = Integer.parseInt(String.valueOf(iplist[i])) & Integer.parseInt(String.valueOf(sublist[j]));
            //数字字符的ASCII码与字符'0'的ASCII码的差值即为该数字字符所表示的数值
            value = ((int) ipChars[i] - chZero) & ((int) maskChars[j] - chZero);
            sb.append(value);
        }

        Long ip = Long.parseLong(sb.toString(), 2);
        return longToIP(ip);
    }

    /**
     * 转换为掩码位数
     *
     * @param mask 点分十进制掩码
     * @return 掩码位数
     */
    public static int subMaskToSubNum(String mask) {
        return getSubnetMaskSizeFromMask(mask);
    }

    /**
     * 根据子网地址(十进制)、子网掩码位数计算网段最后的IP地址
     *
     * @param num 掩码位数
     * @param ip  点分十进制IP
     * @return 点分十进制子网最后一位IP
     */
    public static String getEndSubnetAddress(int num, String ip) {
        return longToIP(getEndAddress(ip + "/" + num));
    }

    /**
     * 将10进制整数形式转换成127.0.0.1形式的IP地址
     *
     * @param longIP IP地址的数值表示
     * @return 点分十进制表示的IP地址
     */
    public static String longToIP(long longIP) {
        StringBuilder sb = new StringBuilder("");
        sb.append(String.valueOf((longIP >>> 24) & 0xFF));// 直接右移24位
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf((longIP & 0x00FFFFFF) >>> 16));
        sb.append(".");
        sb.append(String.valueOf((longIP & 0x0000FFFF) >>> 8));
        sb.append(".");
        sb.append(String.valueOf(longIP & 0x000000FF));
        return sb.toString();
    }

    /**
     * 将127.0.0.1 形式的IP地址转换成10进制整数
     *
     * @param strIP 点分十进制表示的IP地址
     * @return IP地址的数值表示
     */
    private static long ipToLong(String strIP) {
        String[] ipParts = strIP.split("\\.");
        return ipToLong(ipParts);
    }

    /**
     * 将点分十进制形式的IP地址，按点分拆后的字符串数组，转换成long型数值
     *
     * @param ipParts 点分十进制IP的各个小节
     * @return IP地址的数值表示
     */
    private static long ipToLong(String[] ipParts) {
        if (ipParts.length != 4) {
            throw new RuntimeException("Invalid IPv4 address");
        }
        long part0 = Long.parseLong(ipParts[0]);
        long part1 = Long.parseLong(ipParts[1]);
        long part2 = Long.parseLong(ipParts[2]);
        long part3 = Long.parseLong(ipParts[3]);
        if (part0 > 255 || part1 > 255 || part2 > 255 || part3 > 255) {
            throw new RuntimeException("Invalid IPv4 address");
        }
        return (part0 << 24) | (part1 << 16) | (part2 << 8) | part3;
    }

    /**
     * 取出IP地址段中的所有IP
     *
     * @param ipRange IP地址段
     * @return IP列表
     */
    public static List<String> getIpList(String ipRange) {
        long startIp = getBeginAddress(ipRange);
        long endIP = getEndAddress(ipRange);
        List<String> ipList = new LinkedList<>();
        do {
            ipList.add(longToIP(startIp));
            startIp++;
        }
        while (startIp <= endIP);
        return ipList;
    }

    public static List<String> getIpList(String... ipRange) {
        List<String> ipList = new LinkedList<>();
        for (String ip : ipRange) {
            ipList.addAll(getIpList(ip));
        }
        return ipList;
    }

    public static List<String> getIpList(Collection<String> ipRange) {
        List<String> ipList = new LinkedList<>();
        for (String ip : ipRange) {
            ipList.addAll(getIpList(ip));
        }
        return ipList;
    }

    /**
     * 根据Ip地址和子网掩码位数  查询所有该网段的地址
     *
     * @param ip      ip地址（通常是网络地址）
     * @param maskNum 子网掩码位数 （如 24,28,30）
     * @return 网络ip集合
     */
    public static List<String> getIpListByIpMask(String ip, Integer maskNum) {
        return getIpList(ip + "/" + maskNum);
    }

    /**
     * 计算反掩码   255.255.255.0  ==》 0.0.0.255
     *
     * @param mask 掩码255.255.255.0
     * @return 反掩码
     */
    public static String getAntiMask(String mask) {
        if ("0".equals(mask) || "0.0.0.0".equals(mask)) {
            return "255.255.255.255";
        }
        int int_mask = subMaskToSubNum(mask);
        return getAntiMask(int_mask);
    }

    /**
     * 计算反掩码   24  ==》 0.0.0.255
     *
     * @param num 掩码位数
     * @return 反掩码
     */
    public static String getAntiMask(int num) {
        if (0 == num) {
            return "255.255.255.255";
        }

        checkSubnetMaskSize(num);
        long antiMask = (num == 32) ? 0 : (-1 >>> num);
        return longToIP(antiMask);
    }

    /**
     * 根据IP计算子网掩码位数
     *
     * @param ip 目标IP，接受点分十进制IPv4 或者 IPv4 掩码 或者 IPv4/掩码位数 三种格式，无掩码也无掩码位数的默认为32位掩码
     * @return 子网掩码位数
     */
    private static int getSubnetMaskSizeFromIP(String ip) {
        if (ip.contains("-")) {
            return 0;
        }
        if (ip.contains("/")) {
            return getSubnetMaskSizeFromMask(ip.split("/")[1]);
        } else if (ip.contains(" ")) {
            return getSubnetMaskSizeFromMask(ip.split(" ")[1]);
        }
        return 32;
    }

    /**
     * 根据掩码返回掩码位数
     *
     * @param mask 点分十进制表示的子网掩码或者字符串表示的掩码位数
     * @return 子网掩码位数
     */
    private static int getSubnetMaskSizeFromMask(String mask) {
        int maskSize = 0;
        if (mask.contains(".")) {
            int intMask = ~((int) ipToLong(mask));
            while (intMask != 0) {
                intMask = intMask >>> 1;
                maskSize++;
            }
            maskSize = 32 - maskSize;
        } else {
            maskSize = Integer.parseInt(mask);
        }
        checkSubnetMaskSize(maskSize);
        return maskSize;
    }

    /**
     * 拆分出IP和子网掩码
     *
     * @param ipRange 以点分十进制表示的IPv4 或 IPv4 掩码 或 IPv4/掩码位数 表达的IP地址段
     * @return 字符串数组，第一个元素是IP，第二个元素是掩码（或掩码位数）
     */
    private static String[] splitIPAndMask(String ipRange) {
        if (ipRange.contains("/")) {
            return ipRange.split("/");
        } else if (ipRange.contains(" ")) {
            return ipRange.split(" ");
        } else {
            return new String[]{ipRange, "32"};
        }
    }

    /**
     * 验证掩码位数有效性，掩码位数>=1 且 <=32 为有效，否则抛异常
     *
     * @param maskSize 掩码位数
     */
    private static void checkSubnetMaskSize(int maskSize) {
        if (maskSize < 1 || maskSize > 32) {
            throw new RuntimeException("Invalid IPv4 subnet mask");
        }
    }

    /**
     * 验证long型IP地址段起始IP必需小于等于endIP，如不满足则抛出运行时异常
     *
     * @param startIP 起始IP
     * @param endIP   截止IP
     */
    private static void checkLongIPRange(long startIP, long endIP) {
        if (startIP > endIP) {
            throw new RuntimeException("Invalid IPv4 address range");
        }
    }

    /**
     * 将数值表示的IP地址段拼接在一起。即将无序的、散碎的IP地址或IP地址段连接成从小到大有序的，连续的IP地址段
     *
     * @param longRanges 原始的IP地址段，方法运行完后该LinkedList中只有拼接后的IP地段段
     */
    private static void concatLongIPRanges(LinkedList<long[]> longRanges) {
        if (longRanges.size() > 1) {
            //先按起始IP从小到大排序
            Collections.sort(longRanges, new Comparator<long[]>() {
                @Override
                public int compare(long[] o1, long[] o2) {
                    return (int) o1[0] - (int) o2[0];
                }
            });

            //查看后一个IP段是否和前一IP段有衔接
            ListIterator<long[]> rangeIter = longRanges.listIterator();

            //持有最小起始IP的地址段
            long[] offsetRange = rangeIter.next();
            checkLongIPRange(offsetRange[0], offsetRange[1]);

            long[] currentRange = null;
            for (; rangeIter.hasNext(); ) {
                //因已按起始IP从小到大排过序，所以必定是currentRange[0]>=offsetRange[0]，只要判断offsetRange[1]
                currentRange = rangeIter.next();
                checkLongIPRange(currentRange[0], currentRange[1]);

                if (currentRange[1] <= offsetRange[1]) {
                    /* currentRange的截止IP小于等于offsetRange的截止IP，
                    * 则表示offsetRange完全包含currentRange，直接将currentRange从List中移除
                    */
                    rangeIter.remove();
                } else if (currentRange[0] > offsetRange[1] + 1) {
                    /* currentRange的起始IP大于offsetRange的截止IP+1，则表示currentRange与offsetRange之间无衔接
                    * 不会再有其他后续的IP地址段与offsetRange有衔接，
                    * 后续的IP地址段只可能会与currentRange有衔接
                    * 将offsetRange置为currentRange
                    */
                    offsetRange = currentRange;
                } else {
                    /* 两个IP段之间有衔接，
                    * currentRange的截止IP大于offsetRange的截止IP，需要将两段IP“拼接”在一起
                    * 用currentRange的截止IP替换offsetRange的截止IP
                    * 再将currentRange从List中移除
                    */
                    offsetRange[1] = currentRange[1];
                    rangeIter.remove();
                }
            }
        }
    }

    /**
     * 将一个数值型表示的IP地址段，转换成字符串表示的IP地址段
     *
     * @param longRange 数值型IP地址段
     * @return 字符串表示的IP地址段
     */
    private static String longRangeToIPRange(long[] longRange) {
        //起始和截止IP相同，则只有一个IP
        if (longRange[1] == longRange[0]) {
            return longToIP(longRange[0]);
        }
        //验证截止IP大于等于起始IP
        checkLongIPRange(longRange[0], longRange[1]);

        //先判断是否能表示成IP/掩码位数的形式

        //反掩码位数
        int antiMaskSize = 0;
        //反掩码
        long antiMask = 0;
        //暂存反掩码的临时变量，在计算反掩码位数时使用
        long tempLong = 0;
        //标志是否是整个网段
        boolean isWholeSubNet = false;

        //暂存起始IP的各小节
        int[] ipParts = new int[4];
        //暂存截止IP的某小节
        int tmpIpPart = 0;
        //标志是否需要输出截止IP上的该小节
        boolean needThisPart = false;
        //缓存IP地址段的输出字符串
        StringBuilder tmpIPBuf = new StringBuilder(32);

        //通过起始IP和截止IP进行异或计算，判断得到的结果，是否符合反掩码的格式
        antiMask = longRange[0] ^ longRange[1];
        tempLong = antiMask;
        antiMaskSize = 0;
        while ((tempLong & 1) == 1) {
            //最低位为1，则反掩码位数+1，暂存的反掩码向右移1位
            tempLong = tempLong >>> 1;
            antiMaskSize++;
        }

        /* 暂存的反掩码的剩余位都为0，则antiMask符合反掩码的格式
        * 反掩码和起始IP进行按位与操作，结果为0表示起始IP的主机位都为0
        * 反掩码和截止IP进行按位与操作，结果和反掩码相同，则表示截止IP的主机位都为1
        * 同时反掩码位数>0且<32，则掩码位数为>=1且<32（掩码32位的话，符合第一个if条件，即起始IP==截止IP）
        * 此时能断定该IP地址段为整个子网网段
        */
        isWholeSubNet = (tempLong == 0)
                && ((longRange[0] & antiMask) == 0)
                && ((longRange[1] & antiMask) == antiMask)
                && (antiMaskSize > 0 && antiMaskSize < 32);

        //如果是整个网段，则返回IP/掩码位数的形式
        if (isWholeSubNet) {
            return (longToIP(longRange[0]) + "/" + (32 - antiMaskSize));
        }

        //否则，IP地址段表示为xxx.xxx.xxx.xxx-yyy.yyy的形式
        ipParts[0] = (int) ((longRange[0] >>> 24) & 0xFF);
        ipParts[1] = (int) ((longRange[0] >>> 16) & 0xFF);
        ipParts[2] = (int) ((longRange[0] >>> 8) & 0xFF);
        ipParts[3] = (int) (longRange[0] & 0xFF);
        tmpIPBuf.replace(0, tmpIPBuf.length(), "")
                .append(ipParts[0]).append(".")
                .append(ipParts[1]).append(".")
                .append(ipParts[2]).append(".")
                .append(ipParts[3]).append("-");
        needThisPart = false;
        for (int i = 0; i < 4; i++) {
            tmpIpPart = (int) ((longRange[1] >>> ((3 - i) * 8)) & 0xFF);
            needThisPart |= (tmpIpPart != ipParts[i]);
            if (needThisPart) {
                tmpIPBuf.append(tmpIpPart).append(".");
            }
        }
        //去掉最后的.
        return tmpIPBuf.substring(0, tmpIPBuf.length() - 1);
    }

    /**
     * 将数值表示的IP地址段拼接在一起，并转成字符串表示的IP地址段
     *
     * @param longRanges 数值表示的IP地址段
     * @return 点分十进制字符串表示的IP地址段
     */
    private static List<String> longRangesToIPRanges(List<long[]> longRanges) {
        //最终结果
        List<String> ipRanges = new LinkedList<>();

        //先将原始IP地址段有序拼接成连续的IP地址段
        LinkedList<long[]> sortedLongRanges = new LinkedList<>(longRanges);
        concatLongIPRanges(sortedLongRanges);

        for (long[] range : sortedLongRanges) {
            ipRanges.add(longRangeToIPRange(range));
        }

        return ipRanges;
    }

    /**
     * 将数值表示的IP地址段拼接在一起，并转子网地址和子网掩码位数列表
     *
     * @param longRanges  数值表示的IP地址段
     * @param minMaskSize 允许的最小的掩码位数
     * @return 包含了子网地址和子网掩码位数和列表，列表中每个元素为long[]，其中long[0]为子网地址，long[1]为子网掩码位数
     */
    private static List<long[]> longRangesToSubNets(List<long[]> longRanges, int minMaskSize) {
        //用于保存转换结果
        LinkedList<long[]> resultList = new LinkedList<>(longRanges);

        //先拼接IP地址段
        concatLongIPRanges(resultList);

        //暂存子网地址、子网掩码位数的数组
        long[] subNet = null;
        //暂存起止IP的数组
        long[] startEndIP = null;
        //可能的反掩码位数
        int mightAntiMask = 0;
        //实际的反掩码位数
        int realAntiMask = 0;
        //IP个数
        long ipCount = 0;
        //暂存IP
        long longIP = 0;

        //对resultList中的每一个起止IP数组转换成子网和掩码数组
        for (ListIterator<long[]> rangeIter = resultList.listIterator(); rangeIter.hasNext(); ) {
            //处理每个起止IP数组
            startEndIP = rangeIter.next();

            while (startEndIP[0] <= startEndIP[1]) {
                //先根据IP个数判断反掩码可能是多少位
                ipCount = startEndIP[1] - startEndIP[0] + 1;
                mightAntiMask = 0;
                while (ipCount > 1) {
                    ipCount = ipCount >>> 1;
                    mightAntiMask++;
                }

                //再判断当前IP从右到左是否mightAntiMask位全为0
                longIP = startEndIP[0];
                realAntiMask = 0;
                for (; realAntiMask < mightAntiMask; realAntiMask++) {
                    if ((longIP & 1) == 1) {
                        break;
                    }
                    longIP = longIP >>> 1;
                }

                //得到实际反掩码位数，反掩码位数不能大于31
                if (realAntiMask > 31) {
                    throw new RuntimeException("Invalid IPv4 address");
                }
                //将子网地址和子网掩码位数保存到数组中
                subNet = new long[2];
                //求子网地址
                subNet[0] = startEndIP[0] & (-1 << realAntiMask);
                //求掩码位数
                subNet[1] = Math.max(minMaskSize, 32 - realAntiMask);
                realAntiMask = 32 - (int) subNet[1];

                //将指针移到当前startEndIP数组之前
                rangeIter.previous();
                //将子网信息添加到rangeIter中
                rangeIter.add(subNet);
                //添加完毕后再将指针移回到当前startEndIP数组之处
                rangeIter.next();

                //ipIndex要越过已被包含在子网中的IP的下标
                startEndIP[0] += (1 << realAntiMask);
            }

            //当前startEndIP数组中的IP地址已全部处理完毕，将其从rangeIter中删除
            rangeIter.remove();
        }

        //循环完毕，列表中为最终结果
        return resultList;
    }

    /**
     * 将一组IP地址段转换成数值型起止IP数组表示的IP地址段
     *
     * @param ipRanges IP地址段，接受点分十进制IPv4、IPv4 掩码、IPv4/掩码位数、IPv4-IPv4片段的四种格式
     * @return 以数值型起止IP数组表示的IP地址段
     */
    private static List<long[]> ipRangesToLongRanges(String... ipRanges) {
        List<long[]> longList = new ArrayList<>(ipRanges.length);
        for (String range : ipRanges) {
            longList.add(
                    new long[]{
                            getBeginAddress(range),
                            getEndAddress(range)
                    }
            );
        }
        return longList;
    }
}