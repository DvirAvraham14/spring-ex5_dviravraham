function showDiagram1() {
    d3.select("#diagram1").style("display", "none");
    d3.select("#diagram2").style("display", "block");
    generateDiagrama();
}

function showDiagram2() {
    d3.select("#diagram2").style("display", "none");
    d3.select("#diagram1").style("display", "block");
    generateDiagram();
}


function parseExpenses() {
    let expenseArray = document.getElementById("expenses").getAttribute("data-expenses");
    let expenses = expenseArray.split(', Expense');
    let ex = [];
    // Parse dates and amounts
    let parseDate = d3.timeParse("%Y-%m-%d");
    expenses.forEach(function (expense) {
        if (expense.startsWith('[Expense') || expense.endsWith(']')) {
            expense = expense.replace('[Expense', '');
            expense = expense.replace(']', '');
        }
        let date = parseDate(expense.match(/date=(\d{4}-\d{2}-\d{2})/)[1]);
        let amount = +expense.match(/amount=([\d.]+)/)[1];
        let category = expense.match(/category='([^']+)'/)[1];

        let e = {date: date, amount: amount, category: category};
        ex.push(e);
    });

    return ex;
}

function generateDiagram() {
    d3.select("#diagram1").selectAll("*").remove(); // Clear the diagram

    let ex = parseExpenses();
    let categoryTotals = {};
    ex.forEach(function (expense) {
        if (!categoryTotals[expense.category]) {
            categoryTotals[expense.category] = 0;
        }
        categoryTotals[expense.category] += expense.amount;
    });

    // Set up the dimensions of the chart
    let width = 400;
    let height = 400;
    let radius = Math.min(width, height) / 2;
    let donutWidth = 75;
    let legendRectSize = 18;
    let legendSpacing = 4;

    // Set up color scale for categories
    let color = d3.scaleOrdinal(d3.schemeCategory10);

    // Create SVG element
    let svg = d3.select("#diagram1")
        .append("svg")
        .attr("width", width)
        .attr("height", height)
        .append("g")
        .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

    // Define arc for the pie chart
    let arc = d3.arc()
        .innerRadius(radius - donutWidth)
        .outerRadius(radius);

    // Define arc for the category label
    let labelArc = d3.arc()
        .innerRadius(radius - 80)
        .outerRadius(radius - 80);

    // Create a pie layout
    let pie = d3.pie()
        .value(function (d) {
            return d.value;
        })
        .sort(null);

    // Generate data for the pie chart
    let data = [];
    for (let category in categoryTotals) {
        data.push({
            category: category,
            value: categoryTotals[category]
        });
    }

    // Create slices for the pie chart
    let slices = svg.selectAll("path")
        .data(pie(data))
        .enter()
        .append("g");

    // Append paths to the slices
    slices.append("path")
        .attr("d", arc)
        .attr("fill", function (d) {
            return color(d.data.category);
        })
        .attr("stroke", "#fff")
        .attr("stroke-width", "2px");

    // Append labels to the slices with category above the percentage
    slices.append("text")
        .attr("transform", function (d) {
            let centroid = labelArc.centroid(d);
            return "translate(" + centroid + ")";
        })
        .attr("dy", "-0.5em") // Move up by 0.5em (adjust as needed)
        .text(function (d) {
            let percent = ((d.endAngle - d.startAngle) / (2 * Math.PI)) * 100;
            return d.data.category;
        });

    // Append labels to the slices with percentage below the category
    slices.append("text")
        .attr("transform", function (d) {
            let centroid = labelArc.centroid(d);
            return "translate(" + centroid + ")";
        })
        .attr("dy", "0.7em") // Move down by 0.7em (adjust as needed)
        .text(function (d) {
            let percent = ((d.endAngle - d.startAngle) / (2 * Math.PI)) * 100;
            return percent.toFixed(2) + "%";
        });

    // Add legend
    let legend = svg.selectAll(".legend")
        .data(color.domain())
        .enter()
        .append("g")
        .attr("class", "legend")
        .attr("transform", function (d, i) {
            let legendHeight = legendRectSize + legendSpacing;
            let offset = legendHeight * color.domain().length / 2;
            let horz = -2 * legendRectSize;
            let vert = i * legendHeight - offset;
            return "translate(" + horz + "," + vert + ")";
        });

    legend.append("rect")
        .attr("width", legendRectSize)
        .attr("height", legendRectSize)
        .style("fill", color)
        .style("stroke", color);

    legend.append("text")
        .attr("x", legendRectSize + legendSpacing)
        .attr("y", legendRectSize - legendSpacing)
        .text(function (d) {
            return d;
        });
}


function generateDiagrama() {
    d3.select("#diagram2").selectAll("*").remove(); // Clear the diagram

    let ex = parseExpenses();

    // Set up the dimensions of the chart
    let margin = {top: 20, right: 20, bottom: 30, left: 40},
        width = 600 - margin.left - margin.right,
        height = 400 - margin.top - margin.bottom;

// Sort the 'ex' array based on the 'date' property in ascending order
    ex.sort(function (a, b) {
        return a.date - b.date;
    });

// Set up the X-axis scale
    let x = d3.scaleTime()
        .domain(d3.extent(ex, function (expense) {
            return expense.date;
        }))
        .range([0, width]);

    // Set up the Y-axis scale
    let y = d3.scaleLinear()
        .domain([0, d3.max(ex, function (expense) {
            return expense.amount;
        })])
        .range([height, 0]);

// Create the SVG element
    let svg = d3.select("#diagram2")
        .append("svg")
        .attr("width", "100%") // Set the width to 100% of the container
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")"); // Adjust the transform attribute

// Create the line generator
    let line = d3.line()
        .x(function (expense) {
            return x(expense.date);
        })
        .y(function (expense) {
            return y(expense.amount);
        });

// Create the line path
    svg.append("path")
        .datum(ex) // Pass the parsed 'ex' array instead of 'expenses'
        .attr("class", "line")
        .attr("d", line);

// Create the X-axis
    svg.append("g")
        .attr("transform", "translate(0," + height + ")")
        .call(d3.axisBottom(x));

// Create the Y-axis
    svg.append("g")
        .call(d3.axisLeft(y));

// Add X-axis label
    svg.append("text")
        .attr("transform", "translate(" + (width / 2) + "," + (height + margin.top + 10) + ")") // Adjust the transform attribute
        .style("text-anchor", "middle")
        .text("Date");

// Add Y-axis label
    svg.append("text")
        .attr("transform", "rotate(-90)")
        .attr("y", 0 - margin.left)
        .attr("x", 0 - (height / 2))
        .attr("dy", "1em")
        .style("text-anchor", "middle")
        .text("Amount");
}