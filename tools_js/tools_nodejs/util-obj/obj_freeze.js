var freezeObj = (obj) => {
  Object.freeze(obj);
  Object.keys(obj).forEach( (key, value) => {
    if ( typeof obj[key] === 'object' ) {
      freeze( obj[key] );
    }
  });
};

module.exports = {
    freeze: freezeObj
}