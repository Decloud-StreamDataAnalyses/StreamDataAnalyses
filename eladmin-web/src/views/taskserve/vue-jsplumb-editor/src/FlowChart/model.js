import { postTest } from "@/api/data"

let FlowChartJson = {
  nodes: [],
  endpoints: [],
  edges: [],
  head: '',
};
/**
 * 翻译字典 左边为中文 右边为英文
 * @type {{ip: string, port: string, topic: string, parameters: string, datasource1KeyIndex: string, datasource2KeyIndex: string, windowSize: string}}
 */
//前端定义的属性在此要加上去 xml中会自动转换成英文 前面是前端显示的中文属性 后面是xml需要的英文属性名
var attributeToChange ={
  "ip":'ip',
  "端口":"port",
  'topic':'topic',
  "参数类型":"parameters",
  'A流关键索引':'datasource1KeyIndex',
  'B流关键索引':'datasource2KeyIndex',
  '窗口大小':'windowSize',
  '索引':'index',
  '判断条件':'keyword',
  '关键词':'keyword',
  '和索引':'sumIndex',
  'sum索引':'sumIndex',
  '滑动大小':'slideSize',
  'max索引':'sumIndex',
  '最小值': 'min',
  '最大值': 'max',
  'Key索引': 'keyIndex',
  '阈值': 'threshold',
  '名称': 'topic',
};
//翻译字典
function keyChangeTo(str){
  for(var key in attributeToChange){
      if(str == key) return attributeToChange[key].valueOf();
  }
}


//根据节点索引和类型创建新名字
function creatNodeName(nodeIndex,nodeClassname){
  // console.log("进入creatname中了！");
  var newname = nodeClassname; //节点新名字
  var newnamelog = 0;//节点新名字的后缀
  var times = 0;//该节点类型出现的次数
  for(var i = 0; i <= nodeIndex; i++)
  {
    if(FlowChartJson.nodes[i].classname == nodeClassname) {
      times++;
    }
    else continue;
  }
  newnamelog = times;
  newname += newnamelog;
  return newname;
}
//通过sourcepoint找到节点的 index ，classname
function findNodeClassnameBySourcePoint(sourcePointId) {
  var nodeInfo =[{nodeIndex:0,nodeClassname:""}];
  for(var i = 0; i < FlowChartJson.nodes.length; i++)
  {
      var state = 0; //判断是否找到状态
      var sources=new Array();
      sources =  FlowChartJson.nodes[i].points.sources;
      // console.log("sources的个数是："+sources.length);
      for(var j = 0 ;j<sources.length;j++)
      {
          if(sourcePointId == sources[j]){
              state = 1;
              break;
          }
      }
      if(state==1) break;
      // console.log('每个节点中的sourcepoint：', FlowChartJson.nodes[i].points.sources);
      // console.log('每个节点中的targetpoint：', FlowChartJson.nodes[i].points.targets);
  }
    nodeInfo.nodeIndex = i;
    nodeInfo.nodeClassname=FlowChartJson.nodes[i].classname;
    // console.log("找到源点的节点下标："+nodeInfo.nodeIndex);
    // console.log("找到源点的节点classname："+nodeInfo.nodeClassname);
    return nodeInfo;

}

//通过targetpoint找到节点的 index ，classname
function findNodeClassnameByTargetPoint(targetPointId) {
  var nodeInfo =[{nodeIndex:0,nodeClassname:""}];
  for(var i = 0; i < FlowChartJson.nodes.length; i++)
  {
    var state = 0; //判断是否找到状态
    var targets=new Array();
    targets =  FlowChartJson.nodes[i].points.targets;
    // console.log("sources的个数是："+targets.length);
    for(var j = 0 ;j<targets.length;j++)
    {
      if(targetPointId == targets[j]){
        state = 1;
        break;
      }
    }
    if(state==1) break;
    // console.log('每个节点中的sourcepoint：', FlowChartJson.nodes[i].points.sources);
    // console.log('每个节点中的targetpoint：', FlowChartJson.nodes[i].points.targets);
  }
  nodeInfo.nodeIndex = i;
  nodeInfo.nodeClassname=FlowChartJson.nodes[i].classname;
  // console.log("找到目标点的节点下标："+nodeInfo.nodeIndex);
  // console.log("找到目标点的节点classname："+nodeInfo.nodeClassname);
  return nodeInfo;
}

//通过连线信息找到两个节点的信息  返回两个节点的index，classname
function findNodeClassnameByConnections(connections) {
  //存储出发节点和目标节点的下标和classname
  var nodeInfo = [{ sourceIndex:0,
                    sourceClassname:"",
                    targetIndex:0,
                    targetClassname:""
                  }];
  var str = connections.split("&&");
  // console.log(str[0]);
  // console.log(str[1]);
  var sourceNodeInfo = findNodeClassnameBySourcePoint(str[0]);
  var targetNodeInfo = findNodeClassnameByTargetPoint(str[1]);
  // console.log("sourceNodeInfo:"+sourceNodeInfo.nodeIndex+sourceNodeInfo.nodeClassname);
  // console.log("targetNodeInfo:"+targetNodeInfo.nodeIndex+targetNodeInfo.nodeClassname);
  nodeInfo.sourceIndex = sourceNodeInfo.nodeIndex;
  nodeInfo.sourceClassname = sourceNodeInfo.nodeClassname;
  nodeInfo.targetIndex = targetNodeInfo.nodeIndex;
  nodeInfo.targetClassname = targetNodeInfo.nodeClassname;
  // console.log("根据连线信息找到的两个节点信息如下：", nodeInfo);

  return nodeInfo;




}

export default {
  getData() {
    return FlowChartJson;
  },

  setData(data) {
    FlowChartJson = data;
  },

  getEndpoints() {
    return FlowChartJson.endpoints;
  },

  addNode(nodeData) {
    FlowChartJson.nodes.push(nodeData);
  },

  removeNodeByNodeId(nodeId) {
    FlowChartJson.nodes = FlowChartJson.nodes.filter(node => node.id !== nodeId);
  },

  getNodeDataByNodeId(nodeId) {
    return FlowChartJson.nodes.find(n => n.id === nodeId);
  },

  addEdge(edge) {
    FlowChartJson.edges.push(edge);
  },

  removeEdge(edge) {
    FlowChartJson.edges = FlowChartJson.edges.filter(item => item !== edge);
  },

  getEdgesByPointIds(ids) {
    const data = [];
    FlowChartJson.edges.forEach((edge) => {
      ids.forEach((id) => {
        if (edge.indexOf(id) > -1) {
          data.push(edge);
        }
      });
    });
    return data;
  },

  removeEdgesByPointIds(ids) {
    ids.forEach((id) => {
      FlowChartJson.edges = FlowChartJson.edges.filter(edge => edge.indexOf(id) === -1);
    });
  },

  addEndpoint(point) {
    FlowChartJson.endpoints.push(point);
  },

  getEndpointsByPointIds(ids) {
    return [...FlowChartJson.endpoints.filter((point) => {
      if (ids.indexOf(point.id) > -1) {
        return true;
      }
      return false;
    })];
  },

  removeEndpointsByPointIds(ids) {
    ids.forEach((id) => {
      FlowChartJson.endpoints = FlowChartJson.endpoints.filter(point => point.id !== id);
    });
  },

  changeNodePos(nodeId, newPosition) {
    const node = FlowChartJson.nodes.find(n => n.id === nodeId);
    node.position = newPosition;
  },

  changeNodeValue(nodeId, value) {
    const node = FlowChartJson.nodes.find(n => n.id === nodeId);
    node.data.value = value;
  },

  getHead() {
    return FlowChartJson.head;
  },


  getXml(){
    // console.log("字典",attributeToChange);
    //  for(key in attributeToChange){
    //   console.log(key);
    //   console.log(attributeToChange[key].valueOf());
    // }
    var oTasknameInputId = document.getElementById("taskname");
    // console.log("任务名称：",oTasknameInputId.value);
    console.log('custruct xml............');
    var xml = '<?xml version="1.0" encoding="UTF-8" standalone="no"?>\n' +
      '<process version="5.3.000">\n' +
      '<operator activated="true" class="process" compatibility="5.3.000" expanded="true" name="Root">\n';
    xml += ' <process name="'+oTasknameInputId.value+'">\n';
    console.log("目前节点个数："+FlowChartJson.nodes.length);
    console.log("目前连接信息数："+FlowChartJson.edges.length);

    for(var i = 0; i < FlowChartJson.nodes.length; i++)
    {
      // console.log("进入for循环了！");
       var newname = creatNodeName(i,FlowChartJson.nodes[i].classname);//节点的name
       xml+='<operator class="'+FlowChartJson.nodes[i].classname+'"'+ '     name="'+newname+'" >\n';
       // console.log("此节点的class是："+FlowChartJson.nodes[i].classname);
       // console.log("此节点的name是:"+newname);
       // console.log('nodeid:', FlowChartJson.nodes[i].id);//id
      console.log('node:', FlowChartJson.nodes[i]);
       var properties = new Map();
       properties = FlowChartJson.nodes[i].properties;
       // console.log(properties);
       for(var key in properties){
         var keyChange = keyChangeTo(key);
         // console.log("属性名：",key,"翻译之后：",keyChange);
       xml+='<parameter key="'+keyChange+'" value="'+properties[key].valueOf()+'"/>\n';
         // console.log(key);
         // console.log(properties[key].valueOf());
       }
       xml+='</operator>\n'
       // console.log('nodeType:', FlowChartJson.nodes[i].classname);//节点classname
       // console.log('node:', FlowChartJson.nodes[i])
    }
    for(var i = 0; i < FlowChartJson.edges.length; i++)
    {
      console.log('edge:', FlowChartJson.edges[i]);
      var nodeInfo = findNodeClassnameByConnections(FlowChartJson.edges[i]); //返回的是两个节点的信息
      // console.log("找到的两个连线节点信息如下：",nodeInfo);
      var sourceNodeNewname = creatNodeName(nodeInfo.sourceIndex,nodeInfo.sourceClassname);
      var targetNodeNewname = creatNodeName(nodeInfo.targetIndex,nodeInfo.targetClassname);
      console.log("sourceNodeNewname:",sourceNodeNewname);
      console.log("targetNodeNewname:",targetNodeNewname);

      xml+='<connector from_name="'+sourceNodeNewname+'" to_name="'+targetNodeNewname+'"/>\n';

    }
    xml += '</process>\n' +
      '    </operator>\n' +
      '    </process>'  ;
    return xml;
  },

  sendString(postXml){
  this.url = '/api/task/xmlToTask'
  console.log(postXml)
  postTest(this.url, {
    xmlStr : postXml
  }).then(res => {
    alert(res)
    console.log('res:',res)
  }).catch(err => {
    // this.loading = false
    console.log(err.response.data.message)
  })
  // this.axios.post('localhost:8013/api/logs/postTest', 'sendString').then(function(response) {
  //   console.log('11111111111111')
  //   console.log(response)
  // }).catch(function(error) {
  //   console.log(error)
  // })

}

};
