function Captcha(config) {

  /**
   * generateUrl: 生成验证码的Url
   * 要求支持GET请求，返回{image:base64字符串, id} 
   */
  this.generateUrl = config.generateUrl;
  /**
   * verifyURL: 生成验证码的Url，
   * 要求支持GET请求，返回{ code:"", data: object}; 
   * 当 code = 200 时，表示通过
   * 当 code = 403 时，表示错误
   */
  this.verifyURL = config.verifyURL;
  this.captchaId = "";
  this.captchaPopupCurtain = undefined;
  this.captchaPopup = undefined;
  this.captchaInput = undefined;
  this.onSuccess = config.onSuccess;

  /* 初始化弹窗 DOM */
  this.initPopup = () => {


    // 创建验证码弹窗的 HTML 元素
    this.captchaPopup = document.createElement('div');
    this.captchaPopup.id = 'captchaPopup';
    this.captchaPopup.className = 'captcha-popup';

    this.captchaPopupCurtain =  document.createElement('div');
    this.captchaPopupCurtain.className = 'captcha-popup-curtain';

    var captchaPopupInner = document.createElement('div');
    captchaPopupInner.className = 'captcha-popup-inner';

    var captchaPopupTitle = document.createElement('div');
    captchaPopupTitle.innerHTML = "<h3 style='text-align: center;height: 1.2rem;'>请输入图形验证码</h3>"

    var captchaPopupBody = document.createElement('div');
    captchaPopupBody.className = "captcha-popup-body"

    this.captchaInput = document.createElement('input');
    this.captchaInput.id = 'captchaInput';
    this.captchaInput.type = 'text';
    this.captchaInput.placeholder = '输入验证码';

    var captchaImage = document.createElement('img');
    captchaImage.id = 'captchaImage';
    captchaImage.alt = '验证码';
    
    captchaPopupBody.appendChild(this.captchaInput);
    captchaPopupBody.appendChild(captchaImage);


    var verifyButton = document.createElement('button');
    verifyButton.id = 'verifyButton';
    verifyButton.textContent = '验证';

    captchaPopupInner.appendChild(captchaPopupTitle);
    captchaPopupInner.appendChild(captchaPopupBody);
    captchaPopupInner.appendChild(verifyButton);

    this.captchaPopup.appendChild(this.captchaPopupCurtain);
    this.captchaPopup.appendChild(captchaPopupInner);

    document.body.appendChild(this.captchaPopup)

    captchaImage.onclick = this.refreshImg;
    verifyButton.onclick = this.verify;
    this.captchaPopupCurtain.onclick = (_) => this.captchaPopup.style.display="none";
  }

  /* 刷新图片 */
  this.refreshImg = () => {
    // 发送请求
    fetch(this.generateUrl)
      .then(response => response.json())
      .then(data => {
        const captchaImage = data.image;
        this.captchaId = data.id;

        // 将验证码图片显示在页面上
        document.getElementById('captchaImage').src = 'data:image/png;base64,' + captchaImage;

        // 在控制台打印 captchaId
        console.log(this.captchaId);
      })
      .catch(error => {
        console.error('Error:', error);
      });
  }

  /* 验证是否正确 */
  this.verify = () => {
    let captchaValue = this.captchaInput.value;
    fetch(this.verifyURL, {
      method: 'POST',
      body: JSON.stringify({
        id: this.captchaId,
        captcha: captchaValue,
      }),
      headers: {
        'Content-Type': 'application/json'
      },
    })
      .then(res=>res.json())
      .then(function (response) {
        if (response.code == 200) {
          alert('验证码验证成功！');
        } else {
          alert('验证码验证失败，请重试！');
        }
        // 关闭验证码弹窗
        this.captchaPopup.style.display = 'none';
      })
      .catch(function (error) {
        console.error('Error:', error);
      });
  }


  // 绑定element
  this.bind = (elementId) => {
    document.getElementById(elementId).addEventListener("click", (e) => {
      this.captchaPopup.style.display = 'flex';
    })
  }

  this.initPopup();
  this.refreshImg();
}