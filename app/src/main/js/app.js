/**
 * Created by Dominik Schwarz on 18.03.2017.
 */
'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {counter: ''};
        this.onGetCounter = this.onGetCounter.bind(this);
        this.onIncrementCounter = this.onIncrementCounter.bind(this);
    }

    componentWillMount() {
        this.onGetCounter();
    }

    onGetCounter() {
        client({method: 'GET', path: '/counter'}).done(response => {
            this.setState({counter: response.entity.count});
        });
    }

    onIncrementCounter() {
        client({method: 'POST', path: '/counter'});
    }

    render() {
        return (
            <div>
                <GetCounterButton onGetCounter={this.onGetCounter}/>
                <IncrementCounterButton onIncrementCounter={this.onIncrementCounter}/>
                <b>Counter: {this.state.counter}</b>
                <LogoutButton/>
            </div>
        )
    }
}

class GetCounterButton extends React.Component {

    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(e) {
        e.preventDefault();
        this.props.onGetCounter();
    }

    render() {
        return (
            <div>
                <button onClick={this.handleSubmit}>Get Counter</button>
            </div>
        )
    }
}


class IncrementCounterButton extends React.Component {

    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(e) {
        e.preventDefault();
        this.props.onIncrementCounter();
    }

    render() {
        return (
            <div>
                <button onClick={this.handleSubmit}>Increment Counter</button>
            </div>
        )
    }
}

class LogoutButton extends React.Component {

    constructor(props) {
        super(props);
    }

    logout(e) {
        e.preventDefault();
        client({method: 'POST', path: '/logout'}).done(response => {
            window.location.href = '/';
        });
    }

    render() {
        return (
            <div>
                <button onClick={this.logout}>Logout</button>
            </div>
        )
    }
}

ReactDOM.render(
    <App />,
    document.getElementById('react')
);
