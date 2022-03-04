export const getUtlParam = url => {
  url = url ? url : window.location.href.substring(0, window.location.href.length - 2);
  let str = url.substring(url.indexOf('?') + 1);
  let arrs = str.split('&');
  let result = {};
  arrs.forEach(item => {
    let keyArr = item.split('=');
    let name = keyArr[0];
    let value = keyArr[1];
    result[name] = value;
  });
  return result;
}
