import Vue from 'vue'
import ElementUI from 'element-ui'

export const baseHost = 'http://127.0.0.1:8080'
export const executeResult = (httpResult) => {
  switch (httpResult.code) {
    case 0:
      return true;
    case 3:
      toLoginPageFn();
      break;
    case 4:
    case 5:
      warningMessage("no pormassion", httpResult.codeMsg);
      toLoginPageFn();
      break;
    case 10:
      errorMessage("system info", httpResult.operationContentDetails);
      return false;
    case 12:
      warningMessage("system info", httpResult.operationContentDetails);
    case 13:
      warningMessage("system info", httpResult.operationContentDetails);
      return false;
    default:
      warningMessage(httpResult.codeMsg, httpResult.operationContentDetails);
  }
}

export const toLoginPageFn = () => {
  ElementUI.MessageBox.confirm('User expired, return to login', 'tips', {
    confirmButtonText: 'ok',
    cancelButtonText: 'cancel',
    type: 'warning'
  }).then(() => {
    Vue.$router.push({name: 'login'})
  }).catch(() => {
    Vue.$router.push({name: 'login'})
  })

}

export const warningMessage = (title, message) => {
  ElementUI.Message.warning(
    title != null && message != null ? title + ":" + message : title
  )
}

export const errorMessage = (title, message) => {
  ElementUI.Message.error(
    (title != null && message != null) ? title + ":" + message : title
  )
}

export const successMessage = (title, message) => {
  ElementUI.Message.success(
    title != null && message != null ? title + ":" + message : title
  )
}

export const setSession = (key, val) => {
  val = typeof(val) !== 'string' ? JSON.stringify(val) : val
  sessionStorage.setItem(key, val)
}

export const getSession = (key) => {
  return JSON.parse(sessionStorage.getItem(key))
}

export const copyObject = (src, desc) => {
  for (name in desc) {
    desc[name] = src[name];
  }
}

export const resetForm = (formName) => {
  console.log(Vue.$refs[formName])
  if (typeof (Vue.$refs[formName]) !== 'undefined') {
    Vue.$refs[formName].resetFields()
  }
}





