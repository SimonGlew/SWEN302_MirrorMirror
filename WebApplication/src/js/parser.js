var parser;

function toDatabaseDate(stringDate){
  //WANTING: YYYY-MM-DD HH:MM:SS
  //GOT: yyyy-mm-dd_hh-MM-ss
  var firstSplit = stringDate.split('_');
  var dateSplit = firstSplit[0].split('-');
  var timeSplit = firstSplit[1].split('-');

  return new Date(dateSplit[0], dateSplit[1], dateSplit[2], timeSplit[0], timeSplit[1], timeSplit[2], 0);
}
//parser.toDatabaseDate = toDatabaseDate;

function toAndroidDate(date){
  //GOT: YYYY-MM-DD HH:MM:SS
  //WANTING: yyyy-mm-dd_hh-MM-ss

  var firstSplit = date.split(' ');
  var dateSplit = firstSplit[0].split('-');
  var timeSplit = firstSplit[1].split('-');

  return dateSplit[0] + '-' + dateSplit[1] + '-' + dateSplit[2] + '_' + timeSplit[0] + '-' + timeSplit[1] + '-' + timeSplit[2];
}
//parser.toAndroidDate = toAndroidDate;

//module.exports = parser;
