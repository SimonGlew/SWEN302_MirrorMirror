var gulp = require('gulp');
var mocha = require('gulp-mocha');
var gutil = require('gulp-util');
var nodemon = require('gulp-nodemon');
var build = require('gulp-build');
 

var jsFiles = ['**/*.js']

gulp.task('serve', [], function(){
	// gulp.watch(jsFiles, function(){
	// 	gulp.run('mocha');
	// });

	var options = {
		script: 'app.js',
		delayTime: 1,
		watch: jsFiles
	}
	return nodemon(options)
		.on('restart', function(ev){
			gulp.run('mocha');
			console.log("Restarting...");
		})

});

gulp.task('mocha', function(){
	return gulp.src(['./test/*.js'], { read: false })
        .pipe(mocha({ reporter: 'list' }))
        .on('error', gutil.log);
});

gulp.task('build', function() {
  gulp.src('scripts/*.js')
      .pipe(build({ GA_ID: '123456' }))
      .pipe(gulp.dest('dist'))
});