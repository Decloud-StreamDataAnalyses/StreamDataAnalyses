const Mock = require('mockjs');

/**
 * 改前端
 */
Mock.mock('api/getFlowChartData', 'get', () => ({
  nodes: [
    // {
    //   id: 'aaa',
    //   points: {
    //     targets: [],
    //     sources: ['source1', 'source2', 'source10'],
    //   },
    //   position: {
    //     left: 300,
    //     top: 100,
    //   },
    //   data: {
    //     value: '数据源1',
    //     icon: 'el-icon-coin',
    //   },
    //   properties:{
    //         IP: '192.168.1.2',
    //         PORT: '213',
    //         属性3: '任意值'
    //     },
    // },
    // {
    //   id: 'bbb',
    //   points: {
    //     targets: ['target1', 'target2', 'target3'],
    //     sources: ['source3'],
    //   },
    //   position: {
    //     left: 300,
    //     top: 300,
    //   },
    //   data: {
    //     value: '数据预处理1',
    //     icon: 'el-icon-magic-stick',
    //   },
    //   properties:{
    //         IP: '192.168.1.3',
    //         PORT: '215',
    //         属性3: '任意值6'
    //   },
    // },
    // {
    //   id: 'ccc',
    //   points: {
    //     targets: ['ccc111', 'ccc222'],
    //     sources: ['ccc333'],
    //   },
    //   position: {
    //     left: 400,
    //     top: 500,
    //   },
    //   data: {
    //     value: '深度学习1111',
    //     icon: 'el-icon-coin',
    //   },
    //   properties:{
    //         IP: '192.168.1.5',
    //         PORT: '217',
    //   },
    // },
    // {
    //   id: 'ddd',
    //   points: {
    //     targets: ['ddd111'],
    //     sources: ['ddd333', 'ddd222'],
    //   },
    //   position: {
    //     left: 650,
    //     top: 300,
    //   },
    //   data: {
    //     value: '特征1111',
    //     icon: 'el-icon-star-off',
    //     nodeState: 'warning',
    //   },
    // },
  ],
  endpoints: [
    {
      id: 'target1',
      data: {
        value: '输入',
      },
    },
    {
      id: 'target2',
      data: {
        value: '输入1',
      },
    },
    {
      id: 'source1',
      data: {
        value: '输出表1',
      },
    },
    {
      id: 'source2',
      data: {
        value: '输出表2',
      },
    },
    {
      id: 'source3',
      data: {
        value: '输出表',
      },
    },
    {
      id: 'ccc111',
      data: {
        value: '输入c1',
      },
    },
    {
      id: 'ccc222',
      data: {
        value: '输入c2',
      },
    },
    {
      id: 'ccc333',
      data: {
        value: '输出表',
      },
    },
    {
      id: 'source10',
      data: {
        value: '输出表3',
      },
    },
    {
      id: 'target3',
      data: {
        value: '输入3',
      },
    },
    {
      id: 'ddd111',
      data: {
        value: '输入',
      },
    },
    {
      id: 'ddd222',
      data: {
        value: '输出DDD',
      },
    },
    {
      id: 'ddd333',
      data: {
        value: '输出E',
      },
    },
  ],
  edges: [], // 边的对应关系(sourceId -> targetId)
  // edges: ['source1&&target1', 'source2&&target2', 'source3&&ccc111', 'source3&&ccc222'], // 边的对应关系(sourceId -> targetId)
  head: 'aaa',
}));


Mock.mock('api/getMenuData', 'get', () => ([
  {// 一级目录
    label: '数据流',
    id: 'source',
    //二级目录
    children: [{
      label: 'kafka数据源',
      id: 'SourceTopic',
      icon: 'el-icon-coin',
    }],
  },
    //==========================
    {//一级目录
    label: '算法库',
    id: 'preHandle',
      //二级目录
    children:
      [{//二级目录
        label: '流合并/拆分',
        id: 'StreamOperate',
        icon: 'el-icon-magic-stick',
        //三级目录
        children:
          [
            {
              label: '双流内连接',
              id: 'FlowInnerJoin',
              icon: 'el-icon-magic-stick',
            },
          ],
        },
        {//二级目录
          label: '流式处理',
          id: 'preHandle2',
          children: [
            {
              label: '删除列',
              id: 'DeleteColumn',
              icon: 'el-icon-magic-stick',
            }
            , {
              label: '字符串过滤',
              id: 'MyStringFilter',
              icon: 'el-icon-magic-stick',
            }, {
              label: '数值过滤',
              id: 'MyNumberFilter',
              icon: 'el-icon-magic-stick',
            },
            {
              label: '滚动窗口求和',
              id: 'TumbleWindowSum',
              icon: 'el-icon-magic-stick',
            },
            {
              label: '滚动窗口求极值',
              id: 'TumbleWindowMax',
              icon: 'el-icon-magic-stick',
            },
            {
              label: '滑动窗口求和',
              id: 'SlideWindowSum',
              icon: 'el-icon-magic-stick',
            },
            {
              label: '滑动窗口求极值',
              id: 'SlideWindowMax',
              icon: 'el-icon-magic-stick',
            },
      ],
    },
        {//二级目录
      label: '数据预处理',
      id: 'preHandle3',
      children:[
        {
          label: '异常值检测',
          id: 'AnomalyDetection',
          icon: 'el-icon-magic-stick',
        },
        {
          label: '特征编码',
          id: 'LabelEncoder',
          icon: 'el-icon-magic-stick',
        },
        {
          label: '归一化',
          id: 'MinMaxScaler',
          icon: 'el-icon-magic-stick',
        },
      ]
    }, ],
  },

    //==================
    {
    label: '服务化',
    id: 'sign',
    children:
      [{
        label: '写入kafka',
        id: 'SinkTopic',
        icon: 'el-icon-magic-stick',
       },
        {
          label: 'REST',
          id: 'REST',
          icon: 'el-icon-magic-stick',
        },
        {
          label: ' ',
          id: '1',

        },
    //     {
    //   label: '特征重要性评估',
    //   id: 'sign2',
    //   children: [
    //     {
    //       label: '线性模型特征重要性',
    //       id: 'sign21',
    //       icon: 'el-icon-magic-stick',
    //     },
    //   ],
    // },
    //     {
    //   label: '特征选择',
    //   id: 'sign3',
    //   children: [
    //     {
    //       label: '过滤式特征选择',
    //       id: 'sign31',
    //       icon: 'el-icon-magic-stick',
    //     },
    //   ],
    // },
    //     {
    //   label: '特征生成',
    //   id: 'sign4',
      // children: [
      //   {
      //     label: '特征编码',
      //     id: 'sign41',
      //     icon: 'el-icon-magic-stick',
      //   },
      //   {
      //     label: 'one-hot编码',
      //     id: 'sign42',
      //     icon: 'el-icon-magic-stick',
      //   },
      // ],
    // }
    ],
  },

    //==================
  //   {
  //   label: '机器学习',
  //   id: 'learn',
  //   children: [{
  //     label: '二分类',
  //     id: 'learn1',
  //     children: [
  //       {
  //         label: 'GBDT二分类',
  //         id: 'learn11',
  //         icon: 'el-icon-star-off',
  //       },
  //       {
  //         label: '线性支持向量机',
  //         id: 'learn12',
  //         icon: 'el-icon-star-off',
  //       },
  //       {
  //         label: '逻辑回归二分类',
  //         id: 'learn13',
  //         icon: 'el-icon-star-off',
  //       },
  //     ],
  //   }, {
  //     label: '多分类',
  //     id: 'learn2',
  //     children: [
  //       {
  //         label: 'K近邻',
  //         id: 'learn21',
  //         icon: 'el-icon-star-off',
  //       },
  //       {
  //         label: '随机森林',
  //         id: 'learn22',
  //         icon: 'el-icon-star-off',
  //       },
  //       {
  //         label: '朴素贝叶斯',
  //         id: 'learn23',
  //         icon: 'el-icon-star-off',
  //       },
  //     ],
  //   }, {
  //     label: '聚类',
  //     id: 'learn3',
  //     children: [
  //       {
  //         label: 'K均值聚类',
  //         id: 'learn31',
  //         icon: 'el-icon-star-off',
  //       },
  //       {
  //         label: 'DBSCAN',
  //         id: 'learn32',
  //         icon: 'el-icon-star-off',
  //       },
  //     ],
  //   }, {
  //     label: '回归',
  //     id: 'learn4',
  //     children: [
  //       {
  //         label: '线性回归',
  //         id: 'learn41',
  //         icon: 'el-icon-star-off',
  //       },
  //       {
  //         label: 'PS线性回归',
  //         id: 'learn42',
  //         icon: 'el-icon-star-off',
  //       },
  //     ],
  //   }, {
  //     label: '评估',
  //     id: 'learn5',
  //     children: [
  //       {
  //         label: '混淆矩阵',
  //         id: 'learn51',
  //         icon: 'el-icon-star-off',
  //       },
  //       {
  //         label: '多分类评估',
  //         id: 'learn52',
  //         icon: 'el-icon-star-off',
  //       },
  //     ],
  //   }, {
  //     label: '预测',
  //     id: 'learn6',
  //     icon: 'el-icon-star-off',
  //   }],
  // }
  ]
));

Mock.mock('api/getPropertyData', 'get', () => (
{
    String:Ip = '10.5.83.175',
    'SourceTopic':
        {
            ip: Ip,
            端口: 9092,
            topic:'ShowTest1',
            参数类型:'String,Double',
        },
    'FlowInnerJoin':
        {
          A流关键索引: 0,
          B流关键索引: 0,
          窗口大小:10,
        },
    'DeleteColumn':
        {
            索引: 1,
        },
    'MyStringFilter':
    {
      索引: 1,
      关键词: '',

    },
    'MyNumberFilter':
    {
      索引: '',
      判断条件: '>40',
    },
  'TumbleWindowSum':
    {
      窗口大小: 10,
      和索引: '',
    },
  'TumbleWindowMax':
    {
      窗口大小: 10,
      max索引: 'sumIndex',
    },
  'SlideWindowSum':
    {
      窗口大小: 10,
      滑动大小: 5,
      sum索引:1,
    },
  'SlideWindowMax':
    {
      窗口大小: 10,
      滑动大小: 5,
      max索引:'',
    },
  'LabelEncoder':
    {
      索引: '',
    },
  'MinMaxScaler':
    {
      索引:1,
      最小值: 0,
      最大值: 60,
    },
  'AnomalyDetection':
    {
      Key索引: 1,
      阈值:40
    },
  'SinkTopic':
    {
      ip: Ip,
      端口: 9092,
      topic:'AnomalyDetectionTest',
    },
  'REST':
    {
      名称: 'AnomalyDetectionTest1',
    },
}
));
