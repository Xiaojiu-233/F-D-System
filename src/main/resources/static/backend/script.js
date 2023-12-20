// JavaScript文件（script.js）

//后端请求区
var baseUrl = "http://192.168.0.106:8081";
var loadCat = false;
function getData(url,Id){
  fetch(baseUrl + url,{
    method: 'GET'
  }) // 替换成后端提供数据的端点
  .then(response => {
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    return response.json();
  })
  .then(data => {
    console.log(data);
    if(data != undefined && data != null){
      if(data.code == 1){
        renderMenuTable(data.data,Id);
      }else{
        alert(data.msg);
      }
    }
    
  })
  .catch(error => {
    console.error('There has been a problem with your fetch operation:', error);
  });
}
function postData(url,data){
  fetch(baseUrl + url,{
    method: 'POST',
    body: data
  }) // 替换成后端提供数据的端点
  .then(response => {
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    return response.json();
  })
  .then(data => {
    console.log(data);
    if(data != undefined && data != null){
      if(data.code == 1){
        alert(data.data);
        location.reload();
      }else{
        alert(data.msg);
      }
    }
    
  })
  .catch(error => {
    console.error('There has been a problem with your fetch operation:', error);
  });
}
function postJsonData(url,data){
  fetch(baseUrl + url,{
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)
  }) // 替换成后端提供数据的端点
  .then(response => {
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    return response.json();
  })
  .then(data => {
    console.log(data);
    if(data != undefined && data != null){
      if(data.code == 1){
        alert(data.data);
      }else{
        alert(data.msg);
      }
    }
    
  })
  .catch(error => {
    console.error('There has been a problem with your fetch operation:', error);
  });
}
function formSubmit(form,url){
  const Form = document.getElementById(form);
  let formData = new FormData(Form);

  postData(url,formData);
}
function formPhotoSubmit(form,url,f){
  const Form = document.getElementById(form);
  var fileInput = document.getElementById('fileInput' + f);
  let formData1 = new FormData(Form);
  formData1.append('imgUrl', fileInput.files[0].name);
  let formData2 = new FormData();
  formData2.append('file', fileInput.files[0]);
  postData(url,formData1);
  postData('/common/upload',formData2);
}
function renderMenuTable(data,selector) {
  // 获取现有表格
  const table = document.getElementById(selector);
  const tbody = table.querySelector('table').querySelector('tbody');
  // 清空现有表格中的内容
  tbody.innerHTML = '';
  // 填充数据到表格中
  data.forEach(rowData => {
    const row = document.createElement('tr');
    Object.values(rowData).forEach(value => {
      //遍历插入
      const cell = document.createElement('td');
      if(typeof value === 'string' && (value.includes('.jpg') || value.includes('.jpeg'))){
        const img = document.createElement('img');
        img.src = baseUrl + '/common/download/' + value; // 设置图片链接
        img.alt = 'Placeholder Image'; // 设置替代文本
        img.style = "width:100px;height:100px;";
        cell.appendChild(img);
      }else{
        cell.textContent = value;
      }
      row.appendChild(cell);
    });
    //对于每一行，还需要插入特殊作用的按钮
    if(selector == "table"){
      //确定参数
      var keys = Object.keys(rowData);
      var firstKey = keys[0]; // 获取第一个键
      var id = rowData[firstKey];
      //添加按钮
      const btn = document.createElement('button');
      btn.className = 'select-btn-lite';
      btn.style = 'background-color: #8b47f1;';
      btn.onclick = function(){delSubmit(null,"/table/del/" + id)};
      btn.innerHTML = "删除餐桌";
      row.appendChild(btn);
    }else if(selector == "dish"){
      //确定参数
      var keys = Object.keys(rowData);
      var firstKey = keys[0]; // 获取第一个键
      var id = rowData[firstKey];
      //添加按钮
      btn = document.createElement('button');
      btn.className = 'select-btn-lite';
      btn.style = 'background-color: #07ac0f;';
      btn.onclick = function(){openForm_Dish('updDishModal', id )};
      btn.innerHTML = "修改餐品";
      row.appendChild(btn);
    }else if(selector == "cat"){
      //确定参数
      var keys = Object.keys(rowData);
      var firstKey = keys[0]; // 获取第一个键
      var id = rowData[firstKey];
      //添加按钮
      btn = document.createElement('button');
      btn.className = 'select-btn-lite';
      btn.style = 'background-color: #07ac0f;';
      btn.onclick = function(){openForm_Cat('updCatModal', id )};
      btn.innerHTML = "修改分类";
      row.appendChild(btn);
      btn = document.createElement('button');
      btn.className = 'select-btn-lite';
      btn.style = 'background-color: #8b47f1;';
      btn.onclick = function(){delSubmit(null,"/category/del/" + id)};
      btn.innerHTML = "删除分类";
      row.appendChild(btn);
      //给其他的地方也添加东西
      console.log(loadCat);
      if(!loadCat){
        console.log("添加了 " +rowData.name)
        op = document.createElement('option');
        op.innerHTML = rowData.name;
        op.value = id;
        op1 = document.createElement('option');
        op1.innerHTML = rowData.name;
        op1.value = id;
        frame = document.getElementById('category_select1');
        frame.appendChild(op);
        frame = document.getElementById('category_select2');
        frame.appendChild(op1);
      }

    }else if(selector == "orders"){
      //确定参数
      var keys = Object.keys(rowData);
      var firstKey = keys[0]; // 获取第一个键
      var id = rowData[firstKey];
      //添加按钮
      btn = document.createElement('button');
      btn.className = 'select-btn-lite';
      btn.style = 'background-color: #07ac0f;';
      btn.onclick = function(){openForm_Order('updOrderModal', id )};
      btn.innerHTML = "修改订单";
      row.appendChild(btn);
    }
    tbody.appendChild(row);
  });
  if(selector == "cat")
  loadCat = true;
}
function delSubmit(formData,url){
  console.log(formData);
  var confirmation = confirm("确定要删除此项吗？");
  if (confirmation) {
    postData(url,formData);
  } 
}


//代码执行区
function showPage(pageId,url) {
  // 首先隐藏所有页面
  var pages = document.querySelectorAll('.page');
  for (var i = 0; i < pages.length; i++) {
    pages[i].style.display = 'none';
  }
  
  // 只显示指定ID的页面
  var pageToShow = document.getElementById(pageId);
  if (pageToShow) {
    pageToShow.style.display = 'block';
  }
    //向后端拉取相关数据
    if(url != null && url != undefined)
    getData(url,pageId);
}
function openForm(modal) {
  document.getElementById(modal).style.display = 'block';
}
function openForm_Dish(modal,id) {
  document.getElementById(modal).style.display = 'block';
  document.getElementById('updDishForm').dishId.value = id;
}
function openForm_Cat(modal,id) {
  document.getElementById(modal).style.display = 'block';
  document.getElementById('updCatForm').categoryId.value = id;
}
function openForm_Order(modal,id) {
  document.getElementById(modal).style.display = 'block';
  document.getElementById('updOrderForm').orderId.value = id;
}
function closeForm(modal) {
  document.getElementById(modal).style.display = 'none';
}

//初始化执行区
showPage('user','/user/list');
showPage('cat','/category/list')
document.addEventListener('submit', function(event) {
  event.preventDefault(); // 阻止表单默认提交行为
  // 在这里添加其他的逻辑，或者不进行任何操作
});
