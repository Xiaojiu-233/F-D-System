// JavaScript文件（script.js）

//后端请求区
var baseUrl = "http://192.168.0.106:8080";
var dish_list = [];
let sizes = {}; 
var mul_list = []; 
let queryString = window.location.search;
let params = new URLSearchParams(queryString.substring(1));
var orderId = null;
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
function postData(url,data,func){
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
        if(func == null){
          alert(data.data);
          location.reload();
        }
        else
        func(data);
      }else{
        alert(data.msg);
      }
    }
    
  })
  .catch(error => {
    console.error('There has been a problem with your fetch operation:', error);
  });
}
function formJumpSubmit(form,url){
    const Form = document.getElementById(form);
    let formData = new FormData(Form);
  
    postData(url,formData,function(data){alert('欢迎您！'); window.location.href = '/client/main.html?id='+data.data});
}
function formSubmit(form,url){
  const Form = document.getElementById(form);
  let formData = new FormData(Form);
  postData(url,formData);
}
function submitOrder() {
  const orderNote = document.getElementById('orderNote').value;
  console.log(dish_list)
  // 处理提交订单逻辑，
  const Form = document.getElementById('order-form');
  let formData = new FormData(Form);
  // 统计
  for (let i = 0; i < dish_list.length; i++) {  
      if (sizes[dish_list[i]]) {  
          sizes[dish_list[i]] += 1;  
      } else {  
          sizes[dish_list[i]] = 1;  
      }  
  }  
  for (let i = 0; i < dish_list.length; i++) {  
       mul_list.push(sizes[dish_list[i]] == 1 ?'单品' : '重复')
  } 
  // 添加额外数据
  console.log(dish_list);
  formData.append("userid",parseInt(params.get('id')));
  formData.append("ids",dish_list);
  formData.append("size",mul_list);
  formData.append("note",orderNote);
  postData('/order',formData);
}
function renderMenuTable(data,selector) {
  // 获取现有表格
  const vector = document.getElementById(selector);
  // 清空现有表格中的内容
  vector.innerHTML = '';
  // 填充数据到表格中
  data.forEach(rowData => {
    if(selector== 'mycat'){
      innerBody = document.createElement('button');
      innerBody.onclick = function(){showDishes(rowData.categoryId)};
      innerBody.innerHTML = rowData.name;
      vector.appendChild(innerBody);
    }else if(selector== 'mydish'){
      vector.innerHTML += `
          <div class="dish dc${rowData.category}">
              <img src="${baseUrl + "/common/download/" + rowData.imgUrl}" alt="菜品图片">
              <div id="di${rowData.dishId}" class="dish-info">
                  <h4>${rowData.name}</h4>
                  <p>${rowData.price}</p>
              </div>
              <div class="button-box">  
                <button onclick="decrement(${rowData.dishId})">−</button>  
                <span id="c${rowData.dishId}">0</span>  
                <button onclick="increment(${rowData.dishId})">+</button>  
              </div>  
          </div>
      `;
    }else if(selector== 'mycomment'){
      if(rowData.commentScore != null)
        vector.innerHTML += `
              <div class="review-box">
                  <div class="rating"> 
                    <span class="star" title="5星" style="display:${rowData.commentScore >= 5 ? 'inline-block':'none'};"></span>
                    <span class="star" title="5星" style="display:${rowData.commentScore >= 4 ? 'inline-block':'none'};"></span>
                    <span class="star" title="5星" style="display:${rowData.commentScore >= 3 ? 'inline-block':'none'};"></span>
                    <span class="star" title="5星" style="display:${rowData.commentScore >= 2 ? 'inline-block':'none'};"></span>
                    <span class="star" title="5星" style="display:${rowData.commentScore >= 1 ? 'inline-block':'none'};"></span>
                  </div>
                  <p class="review-text">${rowData.commentText == null ? '':rowData.commentText}
                  </p>
              </div>
          `;
    }else if(selector== 'mycom'){
      vector.innerHTML += `                    
      <div class="my-order">
          <h2>我的订单</h2>
          <p class="order-id">订单编号：${rowData.orderId}</p>
          <p class="table-info">餐桌号：${rowData.tables}</p>
          <p class="total-price">总价格：${rowData.amount}</p>
          <button class="submit-review" onclick="CommentBtn(${rowData.orderId})">提交评价</button>
      </div>  `;
    }
    // Object.values(rowData).forEach(value => {
    //   //遍历插入
    //   const cell = document.createElement('td');
    //   cell.textContent = value;
    //   innerBody.appendChild(cell);
    // });

  });
}
function go(id){
  const elementIndex = dish_list.indexOf(id);
  if (elementIndex !== -1) {
    // 如果元素存在，则删除它
    dish_list.splice(elementIndex, 1);
} else {
    // 如果元素不存在，则添加它
    dish_list.push(id);
}
}

//界面处理区
function increment(id) {  
  var counter =  document.getElementById("c" + id).textContent;
  counter++;
  dish_list.push(id);
  document.getElementById("c" + id).textContent = counter;
}  
function decrement(id) {  
  var counter =  document.getElementById("c" + id).textContent;
  if(counter > 0){
    counter--;
    let index = dish_list.indexOf(id);  
    if (index !== -1) {  
      dish_list.splice(id, 1);  
    }  
    document.getElementById("c" + id).textContent = counter;
  }
}  
function showContent(contentId) {
  // 隐藏所有内容
  var contents = document.getElementsByClassName('content');
  for (var i = 0; i < contents.length; i++) {
      contents[i].style.display = 'none';
  }

  // 显示选定的内容
  var selectedContent = document.getElementById(contentId + '-content');
  if (selectedContent) {
      selectedContent.style.display = 'block';
  }
}
function showDishes(catId) {
  // 隐藏所有内容
  var contents = document.getElementsByClassName('dish');
  for (var i = 0; i < contents.length; i++) {
      contents[i].style.display = 'none';
  }

  // 显示选定的内容
  var selectedContent = document.getElementsByClassName('dc'+catId);
  for (var i = 0; i < selectedContent.length; i++) {
    selectedContent[i].style.display = 'flex';
}
}
function showOrderDetails() {
  const selectedDishes = [];
  let totalAmount = 0;
  dish_list.forEach(dishId => {
      const dishes = document.getElementById('di'+dishId);
      const dishName = dishes.querySelector('h4').innerText;
      const dishPrice = parseFloat(dishes.querySelector('p').innerText.match(/\d+/)[0]);
      selectedDishes.push(`${dishName} - ￥${dishPrice}`);
      totalAmount += dishPrice;
  });

  const orderPopup = document.getElementById('orderPopup');
  const selectedDishesElement = document.getElementById('selectedDishes');
  const totalAmountElement = document.getElementById('totalAmount');

  selectedDishesElement.innerText = `已选菜品：\n${selectedDishes.join('\n')}`;
  totalAmountElement.innerText = `总金额：￥${totalAmount.toFixed(2)}`;

  const orderDetails = document.querySelector('.order-details');
  orderDetails.style.display = 'block';
}
function closeOrderDetails() {
  const orderDetails = document.querySelector('.order-details');
  orderDetails.style.display = 'none';
}
// 当点击按钮时显示弹出窗口
function CommentBtn(id) {
  orderId = id;
  document.getElementById("myModal").style.display = "block";
}
// 当点击关闭按钮时隐藏弹出窗口
function CommentClose() {
  document.getElementById("myModal").style.display = "none";
}
// 当点击提交按钮时执行提交操作（这里仅作示例）
function CommentSubmit() {
    const rating = document.querySelector('input[name="rating"]:checked') == null ? 
    1 : document.querySelector('input[name="rating"]:checked').value;
    const reviewText = document.getElementById('review-text').value;
    
    // 在这里执行提交评价的操作，可以将评价发送至后端或进行其他处理
    console.log(rating);
    console.log(reviewText);
    let formData = new FormData();
    formData.append("orderId",orderId);
    formData.append("score",rating);
    formData.append("text",reviewText);
    postData('/order/comment',formData);
}

//初始化执行区
document.addEventListener('submit', function(event) {
  event.preventDefault(); // 阻止表单默认提交行为
  // 在这里添加其他的逻辑，或者不进行任何操作
});
//在这里把所有的数据全拉取了！
getData('/category/list','mycat');
getData('/dish/list','mydish');
getData('/backend/oc','mycomment');
getData('/order/detail/' + parseInt(params.get('id')),'mycom');