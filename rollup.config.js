import resolve from "@rollup/plugin-node-resolve"
import commonjs from "@rollup/plugin-commonjs"
// import postcss from 'rollup-plugin-postcss';
import css from "rollup-plugin-css-porter"; //todo

export default {
  input: 'target/www/js/index.js',
  output: {
    dir: 'target/www/js',
    format: 'umd', /* tbd: check if "iife" or "umd" is better? */
    sourcemap: true/*,
    name: 'astro'*/
  },
  plugins: [
    resolve(),
    commonjs(),
      css()
    // postcss({
    //   extensions: [ '.css' ],
    // })
  ]
};
