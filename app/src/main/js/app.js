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
                <UserInfo/>
                <TransactionDetails/>
                <Transactions/>
                {/*<GetCounterButton onGetCounter={this.onGetCounter}/>*/}
                {/*<IncrementCounterButton onIncrementCounter={this.onIncrementCounter}/>*/}
                {/*<b>Counter: {this.state.counter}</b>*/}
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

class UserInfo extends React.Component {

    constructor(props) {
        super(props);
        this.state = {name: 'no_name',balance:0};
    }
    componentWillMount() {

        client({method: 'GET', path: '/userinfo'}).done(response => {
            this.setState({name: response.entity.username});
            this.setState({balance: response.entity.balance});
        });
    }
    render() {
        return (
            <ul>
                <li>Username: {this.state.name}</li>
                <li>Balance: {this.state.balance} €</li>
            </ul>
        )

    }
};

class TransactionRows extends React.Component {

    render() {
        /* the ES6 version of const data = this.props.data */
        const {data} = this.props;
        /*
         use map to perform the same function on
         each element in the obj array
         */

        const row = data.map((data) =>
            <tr>
                <td>{data.description}</td>
                <td>{data.value}</td>
                <td>{data.owner.username}</td>
                <td>{data.target.username}</td>
                <td><TransactionButton transaction={data}/></td>
            </tr>
        );
        return (
            <span>
                <tr>
                    <th>Id</th>
                    <th>Description</th>
                    <th>Value</th>
                    <th>Owner</th>
                    <th>Target</th>
                </tr>
                {row}
            </span>
        );
    }
}
class TransactionButton extends React.Component {
    constructor(props) {
        super(props);
        this.click = this.click.bind(this);
    }

    click(e) {
        e.preventDefault();
        TransactionDetails.showDetails(this.props.transaction);
    }

    render() {
        return (
            <div>
                <button onClick={this.click}>Show Details</button>
            </div>
        )
    }
}

class Transactions extends React.Component {
    constructor(props) {
        super(props);
        this.state = {transactions: []};
    }
    componentWillMount() {
        client({method: 'GET', path: '/userinfo'}).done(response => {
            this.setState({user: response.entity});
            client({method: 'GET', path: '/transactionsByAccountId?accountid='+response.entity.id}).done(response => {
                this.setState({transactions: response.entity});
            });
        });

    }
    render() {
        return (
                <table>
                    <caption>Transactions:</caption>
                    <TransactionRows data={this.state.transactions} />
                </table>
        )
    }
}

class TransactionDetails extends React.Component {


    constructor(props) {
        super(props);
        this.state = {transaction: null};
        window.details = this;
        // singleton = this;
    }
    static showDetails(newTransaction) {
        if(window.details!=null) {
            window.details.setState({transaction: newTransaction});
            window.details.forceUpdate();
        }

    }

    render() {
        if(this.state.transaction != null) {
            return (
                <ul>
                    <li>Id: {this.state.transaction.id}</li>
                    <li>Value: {this.state.transaction.value}</li>
                    <li>From: {this.state.transaction.owner.username}</li>
                    <li>To: {this.state.transaction.target.username}</li>
                    <li>Created: {this.state.transaction.created}</li>
                    <li>Commited: {this.state.transaction.commited}</li>
                </ul>
            );
        }
        else return (
            <div>
                No transaction selected !
            </div>
        );
    }
}

ReactDOM.render(
    <App />,
    document.getElementById('react')
);

