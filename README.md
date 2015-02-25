FoodChoice-每食
==========
每食是一款帮助用户摆脱选择恐惧症的好助手,每食可以从附近商家中随机选择一家出来,并提供点评以及团购信息.

##Bugs
1.  废弃商铺列表清空后应显示空列表提示语
2.  res文件不应为hardCode风格
3.  FirstButton在定位结束前不应该可点击
4.  版权信息和引用库的版权问题

##需加入的功能
1.  批量恢复废弃商铺
2.  增加选择商铺时的炫酷动画
3.  splash页面就应该定位
4.  可以查看附近的商铺列表
5.  支持设置商铺搜索范围

##测试注意事项
若需要运行测试,请先在[高德SDKn网站](http://lbs.amap.com/api/android-location-sdk/summary/)申请自己的KEY,并将AndroidManifest.xml中`com.amap.api.v2.apikey`的值替换为自己的key(注意在申请KEY时安全码的填写).
设置完毕后才能使用定位服务,否则将一直无法成功定位.

##更多
更多项目信息请参见[Trello Board](https://trello.com/b/kQzmAMLp/foodchoice).

##License

    Copyright 2015 Yifan Zhu

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

