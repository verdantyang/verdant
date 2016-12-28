var path = require('path');

module.exports = function(grunt) {

  grunt.initConfig({
    jshint: {
      all: [],
      options: {
        jshintrc: '.jshintrc'
      }
    },
    compress: {
      main: {
        options: {
          archive: 'deploy.tar.gz',
          mode: 'tgz'
        },
        files: [{
          src: ['package.json',
            'util-http/**'
          ],
          dest: 'out/'
        }]
      }
    },
    clean: ['test.db', 'deploy.tar.gz'],
    sshexec: {
      main: {
        command: 'cd /home/tbadmin && tar xvfz deploy.tar.gz',
        options: {
          host: 'dev-api01.te-buy.com',
          username: 'tbadmin',
          password: '!QAZxcvb'
        }
      },
      clean: {
        command: 'cd /home/tbadmin && rm -rf deploy.tar.gz',
        options: {
          host: 'dev-api01.te-buy.com',
          username: 'tbadmin',
          password: '!QAZxcvb'
        }
      }
    },
    express: {
      options: {},
      test: {
        options: {
          script: path.resolve('bin/www'),
          node_env: 'test'
        }
      }
    },
    mochaTest: {
      test: {
        options: {
          reporter: 'spec'
        },
        src: ['tests/*_test.js']
      }
    }
  });

  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-contrib-compress');
  grunt.loadNpmTasks('grunt-contrib-clean');
  grunt.loadNpmTasks('grunt-ssh');
  grunt.loadNpmTasks('grunt-express-server');
  grunt.loadNpmTasks('grunt-mocha-test');

  grunt.registerTask('deploy', [
    'compress',
    // 'sshexec:main',
    // 'sshexec:clean',
    'clean'
  ]);

  grunt.registerTask('lint', ['jshint']);

  grunt.registerTask('waitTask', 'waiting for some times', function() {
    var done = this.async();
    setTimeout(function() {
      done();
    }, 5000);
  });

  grunt.registerTask('test', [
    // 'clean',
    // 'express:test',
    'mochaTest'
  ]);
};